package wf.poc.ktor2.plugins

import io.ktor.server.application.Application
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import wf.poc.ktor2.service.ProfileService
import wf.poc.ktor2.service.RootService
import wf.poc.ktor2.storage.ProfileStorage

fun Application.configureDI() {

    di {
        import(mainModule, allowOverride = true)
    }
}

val mainModule = DI.Module(name = "mainDI") {
    bind<ProfileStorage>() with singleton { ProfileStorage() }
    bind<ProfileService>() with singleton { ProfileService(instance()) }
    bind<RootService>() with singleton { RootService() }
}
