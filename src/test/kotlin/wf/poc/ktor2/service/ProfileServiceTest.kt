package wf.poc.ktor2.service

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import wf.poc.ktor2.domain.Email
import wf.poc.ktor2.domain.FirstName
import wf.poc.ktor2.domain.LastName
import wf.poc.ktor2.domain.Profile
import wf.poc.ktor2.storage.ProfileStorage
import kotlin.test.assertEquals


internal class ProfileServiceTest {

    private val profileStorage = Mockito.mock(ProfileStorage::class.java)
    private val profileService: ProfileService = ProfileService(profileStorage)
    private val email: Email = Email("jdoe@test.com")
    @Test
    fun `should read profile`(){
        val profile = Profile(email, FirstName("John"), LastName("Doe"))
        Mockito.`when`(profileStorage.getProfile(email)).thenReturn(profile)
        assertEquals(profile, profileService.get(email))
    }
}