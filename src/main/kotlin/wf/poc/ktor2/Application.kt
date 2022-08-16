package wf.poc.ktor2

import io.ktor.server.application.*
import wf.poc.ktor2.plugins.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
// application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureDI()
    configureRouting()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureAdministration()
}
