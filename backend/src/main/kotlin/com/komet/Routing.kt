package com.komet

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
data class ContentResponse(val status: String, val tenantId: String?, val data: JsonElement?)

@Serializable
data class HeroPayload(val title: String, val subtitle: String)

@Serializable
data class RepeaterItem(val id: String, val title: String, val description: String)

@Serializable
data class ServicesPayload(val items: List<RepeaterItem>)

@Serializable
data class SettingsPayload(val theme: String)

@Serializable
data class ContentUpdateResponse(val status: String, val tenantId: String?, val message: String)

@Serializable
data class TenantResponse(val id: String, val name: String, val domain: String, val webhookUrl: String?)

@Serializable
data class TenantCreatePayload(val name: String)

@Serializable
data class TenantUpdatePayload(val webhookUrl: String?)

fun Application.configureRouting() {
    val client = HttpClient(CIO) {
        expectSuccess = false
    }

    routing {
        route("/api/v1") {
            route("/tenants") {
                get {
                    val tenantsList = transaction {
                        Tenants.selectAll().map {
                            TenantResponse(
                                id = it[Tenants.id].value,
                                name = it[Tenants.name],
                                domain = it[Tenants.domain],
                                webhookUrl = it[Tenants.webhookUrl]
                            )
                        }
                    }
                    call.respond(HttpStatusCode.OK, tenantsList)
                }

                route("/{tenantId}") {
                    get {
                        val tenantId = call.parameters["tenantId"] ?: return@get call.respond(
                            HttpStatusCode.BadRequest,
                            mapOf("error" to "Missing tenantId")
                        )

                        val tenant = transaction {
                            Tenants.selectAll().where { Tenants.id eq tenantId }.singleOrNull()
                        }

                        if (tenant != null) {
                            call.respond(
                                HttpStatusCode.OK, TenantResponse(
                                    id = tenant[Tenants.id].value,
                                    name = tenant[Tenants.name],
                                    domain = tenant[Tenants.domain],
                                    webhookUrl = tenant[Tenants.webhookUrl]
                                )
                            )
                        } else {
                            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Tenant not found"))
                        }
                    }

                    put {
                        val tenantId = call.parameters["tenantId"] ?: return@put call.respond(
                            HttpStatusCode.BadRequest,
                            mapOf("error" to "Missing tenantId")
                        )

                        try {
                            val payload = call.receive<TenantUpdatePayload>()

                            val updatedCount = transaction {
                                Tenants.update({ Tenants.id eq tenantId }) {
                                    it[webhookUrl] = payload.webhookUrl
                                }
                            }

                            if (updatedCount > 0) {
                                call.respond(HttpStatusCode.OK, mapOf("success" to "Tenant updated"))
                            } else {
                                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Tenant not found"))
                            }
                        } catch (e: Exception) {
                            call.respond(
                                HttpStatusCode.BadRequest,
                                mapOf("error" to "Invalid payload", "message" to (e.localizedMessage ?: ""))
                            )
                        }
                    }
                }

                post {
                    try {
                        val payload = call.receive<TenantCreatePayload>()
                        val newId = java.util.UUID.randomUUID().toString()

                        val slug = payload.name.lowercase().replace(Regex("[^a-z0-9]+"), "-").trim('-')
                        val dummyDomain = if (slug.isNotEmpty()) "${slug}.komet.local" else "tenant-${newId.substring(0, 8)}.komet.local"

                        val newTenant = transaction {
                            Tenants.insert {
                                it[id] = newId
                                it[name] = payload.name
                                it[domain] = dummyDomain
                            }
                            TenantResponse(newId, payload.name, dummyDomain, null)
                        }
                        call.respond(HttpStatusCode.Created, newTenant)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid payload or database error", "message" to (e.localizedMessage ?: "")))
                    }
                }
            }

            route("/content/{tenantId}/{blockType}") {
                get {
                    val tenantId = call.parameters["tenantId"] ?: return@get call.respond(
                        HttpStatusCode.BadRequest,
                        ContentResponse("error", null, null)
                    )

                    val blockType = call.parameters["blockType"] ?: return@get call.respond(
                        HttpStatusCode.BadRequest,
                        ContentResponse("error", null, null)
                    )

                    val block = transaction {
                        ContentBlocks.selectAll().where {
                            (ContentBlocks.tenantId eq tenantId) and (ContentBlocks.blockType eq blockType)
                        }.singleOrNull()
                    }

                    if (block != null) {
                        val jsonString = block[ContentBlocks.jsonPayload]
                        val jsonElement = Json.parseToJsonElement(jsonString)
                        call.respond(HttpStatusCode.OK, ContentResponse("success", tenantId, jsonElement))
                    } else {
                        call.respond(HttpStatusCode.NotFound, ContentResponse("error", tenantId, null))
                    }
                }

                post {
                    val tenantId = call.parameters["tenantId"] ?: return@post call.respond(
                        HttpStatusCode.BadRequest,
                        ContentUpdateResponse("error", null, "Missing tenantId")
                    )

                    val blockType = call.parameters["blockType"] ?: return@post call.respond(
                        HttpStatusCode.BadRequest,
                        ContentUpdateResponse("error", null, "Missing blockType")
                    )

                    try {
                        val jsonString = when (blockType) {
                            "hero" -> {
                                val payload = call.receive<HeroPayload>()
                                Json.encodeToString(payload)
                            }
                            "services" -> {
                                val payload = call.receive<ServicesPayload>()
                                Json.encodeToString(payload)
                            }
                            "settings" -> {
                                val payload = call.receive<SettingsPayload>()
                                Json.encodeToString(payload)
                            }
                            else -> return@post call.respond(
                                HttpStatusCode.BadRequest,
                                ContentUpdateResponse("error", tenantId, "Unknown blockType")
                            )
                        }

                        val webhookUrl = transaction {
                            val existing = ContentBlocks.selectAll().where {
                                (ContentBlocks.tenantId eq tenantId) and (ContentBlocks.blockType eq blockType)
                            }.singleOrNull()

                            if (existing != null) {
                                ContentBlocks.update({ ContentBlocks.id eq existing[ContentBlocks.id] }) {
                                    it[jsonPayload] = jsonString
                                }
                            } else {
                                ContentBlocks.insert {
                                    it[ContentBlocks.tenantId] = tenantId
                                    it[pageRoute] = "/"
                                    it[ContentBlocks.blockType] = blockType
                                    it[jsonPayload] = jsonString
                                }
                            }

                            val tenant = Tenants.selectAll().where { Tenants.id eq tenantId }.singleOrNull()
                            tenant?.get(Tenants.webhookUrl)
                        }

                        if (!webhookUrl.isNullOrBlank()) {
                            launch {
                                try {
                                    client.post(webhookUrl)
                                } catch (ex: Exception) {
                                    application.environment.log.error("Failed to trigger webhook for tenant $tenantId: ${ex.message}")
                                }
                            }
                        }

                        call.respond(HttpStatusCode.OK, ContentUpdateResponse("success", tenantId, "Content block saved successfully"))
                    } catch (e: Exception) {
                        call.respond(
                            HttpStatusCode.InternalServerError,
                            ContentUpdateResponse("error", tenantId, e.localizedMessage ?: "Unknown error")
                        )
                    }
                }
            }
        }
    }
}
