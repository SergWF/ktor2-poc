package wf.poc.ktor2.route

import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.pattern
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import kotlinx.serialization.Serializable
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import wf.poc.ktor2.domain.Email
import wf.poc.ktor2.domain.FirstName
import wf.poc.ktor2.domain.LastName
import wf.poc.ktor2.domain.Profile
import wf.poc.ktor2.domain.emailRegex
import wf.poc.ktor2.plugins.Validatable
import wf.poc.ktor2.plugins.getPrincipal
import wf.poc.ktor2.plugins.toKtor
import wf.poc.ktor2.security.user
import wf.poc.ktor2.service.ProfileService

fun Route.profileRoute() {

    val profileService by closestDI().instance<ProfileService>()

    route("/profile") {
        get {
            profileService.get(call.authentication.user.email).toDto().let { profile ->
                call.response.status(HttpStatusCode.OK) //not necessary, default value
                call.respond(profile)
            }
        }
        put {
            val dto = call.receive<ProfileDto>()
            profileService.create(dto.toEntity())
            call.response.status(HttpStatusCode.Created)
        }
        post {
            val dto = call.receive<ProfileDto>()
            profileService.update(dto.toEntity())
            call.response.status(HttpStatusCode.OK)
        }
        delete {
            val email = call.authentication.user.email
            profileService.delete(email)
            call.response.status(HttpStatusCode.OK)
        }
    }
}

@Serializable
data class ProfileDto(
    val email: String?,
    val firstName: String?,
    val lastName: String?
) : Validatable {
    fun toEntity(): Profile {
        return Profile(
            Email(email ?: throw IllegalArgumentException("Email can't be null")),
            FirstName(firstName ?: throw IllegalArgumentException("FirstName can't be null")),
            LastName(lastName ?: throw IllegalArgumentException("LastName can't be null"))
        )
    }

    override fun validate(): ValidationResult =
        Validation {
            ProfileDto::email required {
                pattern(emailRegex) hint "should be a valid email value"
            }
            ProfileDto::lastName required {
                minLength(2)  hint "should be at least 2 chars long"
                maxLength(256)  hint "should be max 256 chars long"
            }
            ProfileDto::firstName required {
                minLength(2)  hint "should be at least 2 chars long"
                maxLength(256)  hint "should be max 256 chars long"
            }
        }.validate(this).toKtor()
}

private fun Profile.toDto(): ProfileDto = ProfileDto(email.value, firstName.value, lastName.value)
