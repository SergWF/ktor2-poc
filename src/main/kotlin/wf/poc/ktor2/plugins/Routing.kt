package wf.poc.ktor2.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import wf.poc.ktor2.route.profileRoute
import wf.poc.ktor2.service.ProfileService

fun Application.configureRouting() {

    val profileService = ProfileService()
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        profileRoute(profileService)

    }
}

