package wf.poc.ktor2.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.slf4j.LoggerFactory
import wf.poc.ktor2.route.profileRoute
import wf.poc.ktor2.route.rootRoute

fun Application.configureRouting() {
    val logger = LoggerFactory.getLogger("Application.configureRouting")
    logger.debug("===> configure routing start")
    routing {
        logger.debug("===> configure routing")
        rootRoute()
        profileRoute()
    }
    logger.debug("===> configure routing end")
}

