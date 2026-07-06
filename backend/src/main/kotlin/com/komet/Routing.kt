package com.komet

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ContentResponse(val status: String, val tenantId: String?, val data: List<String>)

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
                    val tenantId = call.parameters["tenantId"]
                    // Dummy response. Later, accept JSON payload and update ContentBlock in DB.
                    call.respond(ContentUpdateResponse("success", tenantId, "Content block updated"))
                }
            }
        }
    }
}
