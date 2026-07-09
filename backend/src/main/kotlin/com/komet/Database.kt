package com.komet

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Tenants : IdTable<String>("tenants") {
    override val id = varchar("id", 255).entityId()
    val name = varchar("name", 255)
    val domain = varchar("domain", 255)
}

object ContentBlocks : IntIdTable("content_blocks") {
    val tenantId = reference("tenant_id", Tenants)
    val pageRoute = varchar("page_route", 255)
    val blockType = varchar("block_type", 100)
    val jsonPayload = text("json_payload")
    val sortOrder = integer("sort_order").default(0)
}

fun initDatabase(jdbcUrl: String = "jdbc:sqlite:komet_cms.db") {
    val database = Database.connect(jdbcUrl, "org.sqlite.JDBC")
    transaction(database) {
        SchemaUtils.create(Tenants, ContentBlocks)

        // Insert dummy tenant if it doesn't exist
        val dummyTenantId = "tenant-123"
        val existingTenant = Tenants.selectAll().where { Tenants.id eq dummyTenantId }.singleOrNull()
        if (existingTenant == null) {
            Tenants.insert {
                it[id] = dummyTenantId
                it[name] = "Demo Praxis"
                it[domain] = "demo-praxis.komet.cms"
            }
        }
    }
}
