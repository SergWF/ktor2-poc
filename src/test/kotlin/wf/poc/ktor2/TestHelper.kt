package wf.poc.ktor2

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.mockito.Mockito
import wf.poc.ktor2.plugins.configureAdministration
import wf.poc.ktor2.plugins.configureHTTP
import wf.poc.ktor2.plugins.configureMonitoring
import wf.poc.ktor2.plugins.configureRouting
import wf.poc.ktor2.plugins.configureSecurity
import wf.poc.ktor2.plugins.configureSerialization
import wf.poc.ktor2.service.RootService

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.testModule() {
    configureTestDI()
    configureRouting()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureAdministration()
}

fun Application.configureTestDI() {
    install(Koin) {
        slf4jLogger()
        modules(testDI)
    }
}

val testDI = module {
    single { Mockito.mock(RootService::class.java) }
}