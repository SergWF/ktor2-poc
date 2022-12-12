package wf.poc.ktor2.route

import io.ktor.server.plugins.requestvalidation.ValidationResult.Invalid
import io.ktor.server.plugins.requestvalidation.ValidationResult.Valid
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ProfileDtoTest {

    @Test
    fun `validateSuccess`() {
        val profileDto = ProfileDto("jdoe@test.de", "john", "doe")
        assertEquals(profileDto.validate(), Valid)
    }

    @Test
    fun `validateFailedByEmail`() {
        val profileDto = ProfileDto("jdoe_test.de", "john", "doe")
        val validate = profileDto.validate()
        assertTrue(validate is Invalid)
        assertEquals((validate as Invalid).reasons, listOf(".email -> should be a valid email value"))
    }

    @Test
    fun `validateFailedByEmailAndLastNameAndFirstName`() {
        val profileDto = ProfileDto("jdoe_test.de", "j", "d".repeat(257))
        val validate = profileDto.validate()
        val expectedErrors = listOf(
            ".email -> should be a valid email value",
            ".firstName -> should be at least 2 chars long",
            ".lastName -> should be max 256 chars long"
        )
        assertTrue(validate is Invalid)
        assertEquals((validate as Invalid).reasons.sorted(), expectedErrors.sorted())
    }
}