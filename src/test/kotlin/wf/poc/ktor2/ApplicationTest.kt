package wf.poc.ktor2

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import wf.poc.ktor2.service.RootService
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest : KoinTest {

    private val rootService: RootService by inject()
//
//    @JvmField
//    @RegisterExtension
//    val koinTestExtension = KoinTestExtension.create {
//        modules(
//            module {
//                single { RootService() }
//            })
//    }
//
//    @JvmField
//    @RegisterExtension
//    val mockProvider = MockProviderExtension.create { clazz -> Mockito.mock(clazz.java) }

//    @BeforeEach
//    fun setUp(){
//        stopKoin()
//    }

    @Test
    fun testRoot() = testApplication {
        application {}
        val expectedGreeting = "test greeting"

//        declareMock<RootService> {
        Mockito.`when`(rootService.getGreeting()).thenReturn(expectedGreeting)
//        }

        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(expectedGreeting, bodyAsText())
        }

    }
}