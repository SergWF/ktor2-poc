package wf.poc.ktor2.route

import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.application
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import wf.poc.ktor2.service.RootService

fun Route.rootRoute() {
    val rootService by closestDI().instance<RootService>()

    route("/") {
        get {
            call.respondText(rootService.getGreeting())
        }

    }
}
