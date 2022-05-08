package wf.poc.ktor2.plugins

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS

fun Application.configureHTTP() {
    install(CORS) {
        methods.add(HttpMethod.Options)
        methods.add(HttpMethod.Put)
        methods.add(HttpMethod.Delete)
        methods.add(HttpMethod.Patch)
        headers.add(HttpHeaders.Authorization)
        headers.add("MyCustomHeader")
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

}
