package wf.poc.ktor2.route;

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import io.ktor.util.reflect.typeInfo
import io.mockk.every
import io.mockk.justRun
import io.mockk.verify
import wf.poc.ktor2.domain.Email
import wf.poc.ktor2.domain.FirstName
import wf.poc.ktor2.domain.LastName
import wf.poc.ktor2.domain.Profile
import wf.poc.ktor2.plugins.configureRouting
import wf.poc.ktor2.security.User
import kotlin.test.Test
import kotlin.test.assertEquals

class ProfileRouteTest : BaseRouteTest() {
    private val email = Email("jdoe@test.com")
    private val firstName = FirstName("John")
    private val lastName = LastName("Doe")
    private val principal = User(email, firstName, lastName)
    private val profile = Profile(email, firstName, lastName)
    private val profileDto = ProfileDto(email.value, firstName.value, lastName.value)

    @Test
    fun testDeleteProfile() = withTestApplication(principal) {
        justRun { profileService.delete(email) }
        client.delete("/profile").apply {
            assertEquals(OK, call.response.status)
            verify { profileService.delete(email) }
        }
    }

    @Test
    fun testGetProfile() = withTestApplication(principal) {
        every { profileService.get(email) } returns profile
        jsonClient.get("/profile").apply {
            assertEquals(OK, call.response.status)
            assertEquals(profileDto, call.response.body(typeInfo<ProfileDto>()))
        }
    }

    @Test
    fun testPostProfile() = withTestApplication(principal) {
        justRun { profileService.update(profile) }
        jsonClient.post("/profile") {
            contentType(ContentType.Application.Json)
            setBody(profileDto)
        }.apply {
            assertEquals(OK, call.response.status)
            verify {profileService.update(profile)}
        }
    }

    @Test
    fun testPutProfile() = withTestApplication(principal) {
        justRun { profileService.create(profile) }
        jsonClient.put("/profile") {
            contentType(ContentType.Application.Json)
            setBody(profileDto)
        }.apply {
            assertEquals(Created, call.response.status)
            verify {profileService.create(profile)}
        }
    }
}