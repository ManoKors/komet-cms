package com.komet

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
data class ContentResponse(val status: String, val tenantId: String?, val data: List<String>)

@Serializable
data class HeroPayload(val title: String, val subtitle: String)

@Serializable
data class ContentUpdateResponse(val status: String, val tenantId: String?, val message: String)

fun Application.configureRouting() {
    routing {
        route("/api/v1") {
            route("/content/{tenantId}") {
                get {
                    val tenantId = call.parameters["tenantId"]
                    // Dummy response. Later, fetch ContentBlocks for tenantId from DB.
                    call.respond(ContentResponse("success", tenantId, emptyList()))
                }

                post {
                    val tenantId = call.parameters["tenantId"] ?: return@post call.respond(
                        HttpStatusCode.BadRequest,
                        ContentUpdateResponse("error", null, "Missing tenantId")
                    )

                    try {
                        val payload = call.receive<HeroPayload>()
                        val jsonString = Json.encodeToString(payload)

                        transaction {
                            ContentBlocks.insert {
                                it[ContentBlocks.tenantId] = tenantId
                                it[pageRoute] = "/"
                                it[blockType] = "hero"
                                it[jsonPayload] = jsonString
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
