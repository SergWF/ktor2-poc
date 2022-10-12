package wf.poc.ktor2

import io.ktor.server.application.Application
import org.slf4j.LoggerFactory
import wf.poc.ktor2.plugins.configureAdministration
import wf.poc.ktor2.plugins.configureDI
import wf.poc.ktor2.plugins.configureHTTP
import wf.poc.ktor2.plugins.configureMonitoring
import wf.poc.ktor2.plugins.configureRequestValidation
import wf.poc.ktor2.plugins.configureRouting
import wf.poc.ktor2.plugins.configureSecurity
import wf.poc.ktor2.plugins.configureSerialization

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
// application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val logger = LoggerFactory.getLogger("Application.module")
    logger.debug("=====MAIN MODULE===")
    configureDI()
    configureRouting()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureAdministration()
//    configureRequestValidation()
}
