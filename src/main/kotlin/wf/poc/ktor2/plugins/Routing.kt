package wf.poc.ktor2.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import wf.poc.ktor2.route.profileRoute
import wf.poc.ktor2.route.rootRoute

fun Application.configureRouting() {

    routing {
        rootRoute()
        profileRoute()
    }
}

