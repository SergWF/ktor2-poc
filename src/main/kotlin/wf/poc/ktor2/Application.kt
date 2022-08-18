package wf.poc.ktor2

import io.ktor.server.application.*
import io.ktor.util.logging.Logger
import org.slf4j.LoggerFactory
import wf.poc.ktor2.plugins.*

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
}
