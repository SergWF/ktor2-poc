package wf.poc.ktor2.route

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import wf.poc.ktor2.domain.Email
import wf.poc.ktor2.domain.FirstName
import wf.poc.ktor2.domain.LastName
import wf.poc.ktor2.domain.Profile
import wf.poc.ktor2.plugins.user
import wf.poc.ktor2.service.ProfileService

fun Route.profileRoute(profileService: ProfileService) {
    route("/profile") {
        authenticate("keycloak") {
            get {
                profileService.get(call.authentication.user).toDto().let { call.respond(it) }
            }
            put {
                val dto = call.receive<ProfileDto>()
                profileService.create(dto.toEntity())
                call.response.status(HttpStatusCode.Created)
            }
            post {
                val dto = call.receive<ProfileDto>()
                profileService.update(dto.toEntity())
                call.response.status(HttpStatusCode.Accepted)
            }
            delete {
                profileService.delete(Email("john.doe@test.com"))
            }
        }
    }
}

@kotlinx.serialization.Serializable
data class ProfileDto(
    val email: Email,
    val firstName: FirstName,
    val lastName: LastName
) {
    constructor(profile: Profile) : this(profile.email, profile.firstName, profile.lastName)

    fun toEntity(): Profile = Profile(email, firstName, lastName)
}

fun Profile.toDto(): ProfileDto = ProfileDto(this.email, this.firstName, this.lastName)
