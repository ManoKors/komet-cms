package com.komet

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object Tenants : IntIdTable("tenants") {
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

fun initDatabase() {
    val database = Database.connect("jdbc:sqlite:komet_cms.db", "org.sqlite.JDBC")
    transaction(database) {
        SchemaUtils.create(Tenants, ContentBlocks)
    }
}
