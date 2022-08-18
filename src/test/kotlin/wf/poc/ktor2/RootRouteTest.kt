package wf.poc.ktor2

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import io.mockk.every
import io.mockk.mockk
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import wf.poc.ktor2.plugins.configureHTTP
import wf.poc.ktor2.plugins.configureRouting
import wf.poc.ktor2.plugins.configureSecurity
import wf.poc.ktor2.plugins.configureSerialization
import wf.poc.ktor2.service.RootService
import kotlin.test.Test
import kotlin.test.assertEquals

class RootRouteTest {

    private val rootService: RootService = mockk()

    @Test
    fun `root GET should return mocked value`() = testApplication {
        val expected = "Expected greeting"
        every { rootService.getGreeting() } returns expected
        application {
            di {
                bind<RootService>() with singleton { rootService }
            }
            configureRouting()
            configureHTTP()
            configureSecurity()
            configureSerialization()
        }

        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(expected, bodyAsText())
        }

    }
}