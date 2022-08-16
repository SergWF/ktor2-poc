package wf.poc.ktor2.service

import org.slf4j.LoggerFactory
import wf.poc.ktor2.domain.Email
import wf.poc.ktor2.domain.FirstName
import wf.poc.ktor2.domain.LastName
import wf.poc.ktor2.domain.Profile
import wf.poc.ktor2.plugins.User
import wf.poc.ktor2.storage.ProfileStorage

class ProfileService(private val profileStorage: ProfileStorage) {
    companion object {

        @JvmStatic
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val LOG = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    fun get(email: Email): Profile = profileStorage.getProfile(email)

    fun create(profile: Profile) {
        LOG.info("CREATE new Profile: {}", profile)
        profileStorage.saveProfile(profile)
    }

    fun update(profile: Profile) {
        LOG.info("update existing Profile: {}", profile)
        profileStorage.saveProfile(profile)
    }

    fun delete(email: Email) {
        LOG.info("DELETE Profile: {}", email)
        profileStorage.deleteProfile(email)
    }
}