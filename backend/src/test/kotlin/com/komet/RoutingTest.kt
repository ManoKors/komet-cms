package com.komet

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.sql.DriverManager

class RoutingTest {

    // Setup an isolated database for each test, but we keep the connection ALIVE
    // during the test by keeping a reference to it, and using it for Exposed!
    // Since Ktor's `testApplication` runs everything, we can just intercept the `initDatabase`
    // by NOT calling it, and connecting our own! But wait, `Application.kt` has `initDatabase` in `module()`, but here we construct our own `application { ... }` block!
    // So we don't call `Application.module()`. We only call `configurePlugins()` and `configureRouting()`.
    // And we set the default Database by calling `Database.connect`.

    // To ensure the memory database survives across different `transaction { }` blocks
    // in Ktor endpoints, we must provide a single connection pool or use `Database.connect({ conn })`
    // BUT every `transaction` block closes the connection! If the connection is closed, the memory db dies.
    // However, if we wrap the ENTIRE test in ONE transaction, it works! Wait, testApplication makes HTTP calls asynchronously.
    // Or we create a connection wrapper that IGNORES close()!

    class KeepAliveConnection(private val delegate: java.sql.Connection) : java.sql.Connection by delegate {
        override fun close() {
            // Do not close so memory db survives!
        }
        fun realClose() {
            delegate.close()
        }
    }

    private lateinit var memoryConnection: KeepAliveConnection

    @Before
    fun setup() {
        // Create exactly what the user wants: an in memory sqlite
        val conn = DriverManager.getConnection("jdbc:sqlite::memory:")
        memoryConnection = KeepAliveConnection(conn)

        // Tell Exposed to use this connection factory
        Database.connect({ memoryConnection })

        // Initialize schema and data
        transaction {
            SchemaUtils.create(Tenants, ContentBlocks)
            val existingTenant = Tenants.selectAll().where { Tenants.id eq "test-tenant" }.singleOrNull()
            if (existingTenant == null) {
                Tenants.insert {
                    it[id] = "test-tenant"
                    it[name] = "Test Tenant"
                    it[domain] = "test-tenant.komet.cms"
                }
            }
            ContentBlocks.deleteAll()
        }
    }

    @Test
    fun testGet404ForMissingContent() = testApplication {
        application {
            configurePlugins()
            configureRouting()
        }
        val response = client.get("/api/v1/content/test-tenant/hero")
        assertEquals(HttpStatusCode.NotFound, response.status)
        val body = response.bodyAsText()
        assertTrue(body.contains("\"status\": \"error\"") || body.contains("\"status\":\"error\""))
    }

    @Test
    fun testPostInsertNewContent() = testApplication {
        application {
            configurePlugins()
            configureRouting()
        }

        val payload = HeroPayload(title = "Test Title", subtitle = "Test Subtitle")
        val jsonPayload = Json.encodeToString(payload)

        val response = client.post("/api/v1/content/test-tenant/hero") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(jsonPayload)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.bodyAsText()
        assertTrue(body.contains("\"status\": \"success\"") || body.contains("\"status\":\"success\""))
    }

    @Test
    fun testPostUpsertExistingContent() = testApplication {
        application {
            configurePlugins()
            configureRouting()
        }

        // 1. Initial POST
        val initialPayload = HeroPayload(title = "Initial Title", subtitle = "Initial Subtitle")
        client.post("/api/v1/content/test-tenant/hero") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Json.encodeToString(initialPayload))
        }

        // 2. Upsert POST
        val updatedPayload = HeroPayload(title = "Updated Title", subtitle = "Updated Subtitle")
        val response = client.post("/api/v1/content/test-tenant/hero") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Json.encodeToString(updatedPayload))
        }

        assertEquals(HttpStatusCode.OK, response.status)

        // 3. Verify GET returns updated data
        val getResponse = client.get("/api/v1/content/test-tenant/hero")
        assertEquals(HttpStatusCode.OK, getResponse.status)
        val getBody = getResponse.bodyAsText()
        assertTrue(getBody.contains("Updated Title"))
        assertTrue(getBody.contains("Updated Subtitle"))
    }

    @Test
    fun testValidationErrorForInvalidPayload() = testApplication {
        application {
            configurePlugins()
            configureRouting()
        }

        val invalidJson = """{"invalidField": "Some Value"}"""

        val response = client.post("/api/v1/content/test-tenant/hero") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(invalidJson)
        }

        val body = response.bodyAsText()
        assertTrue(body.contains("\"status\": \"error\"") || body.contains("\"status\":\"error\""))
    }
}
