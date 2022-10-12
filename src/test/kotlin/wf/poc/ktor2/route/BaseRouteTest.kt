package wf.poc.ktor2.route

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import io.mockk.mockk
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import wf.poc.ktor2.plugins.AuthenticationGroup.POWER_USER
import wf.poc.ktor2.plugins.AuthenticationGroup.REGULAR_USER
import wf.poc.ktor2.plugins.configureHTTP
import wf.poc.ktor2.plugins.configureRouting
import wf.poc.ktor2.plugins.configureSerialization
import wf.poc.ktor2.security.User
import wf.poc.ktor2.service.ProfileService

abstract class BaseRouteTest {

    internal val profileService: ProfileService = mockk()

    fun withTestApplication(user: User, testCase: suspend ApplicationTestBuilder.() -> Unit) = testApplication {

        application {
            di {
                bind<ProfileService>() with singleton { profileService }
            }
            configureRouting()
            configureMockedSecurity(user)
            configureHTTP()
            configureSerialization()
        }
        testCase.invoke(this)
    }
}

val ApplicationTestBuilder.jsonClient: HttpClient
 get() = this.createClient { install(ContentNegotiation) {json()} }

fun Application.configureMockedSecurity(authUser: User) {
    authentication {
        test(REGULAR_USER.name) { principal = authUser }
        test(POWER_USER.name) { principal = authUser }
    }
}

