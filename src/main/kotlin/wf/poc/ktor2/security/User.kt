package wf.poc.ktor2.security

import io.ktor.server.auth.AuthenticationContext
import io.ktor.server.auth.Principal
import io.ktor.server.auth.jwt.JWTPrincipal
import wf.poc.ktor2.domain.Email
import wf.poc.ktor2.domain.FirstName
import wf.poc.ktor2.domain.LastName

data class User(
    val email: Email,
    val firstName: FirstName,
    val lastName: LastName
): Principal

private val AuthenticationContext.jwtPrincipal: JWTPrincipal
    get() = this.principal as JWTPrincipal

private fun JWTPrincipal.getNotNull(key: String): String =
    this[key] ?: throw IllegalStateException("$key can not be empty")

private val JWTPrincipal.user: User
    get() = User(
        email = Email(this.getNotNull("email")),
        firstName = FirstName(getNotNull("given_name")),
        lastName = LastName(getNotNull("family_name"))
    )

val AuthenticationContext.user: User
    get() = when (principal) {
        is JWTPrincipal -> (principal as JWTPrincipal).user
        is User -> principal as User
        else -> throw IllegalStateException("Unknown AuthenticationContext type. User information can't be received")
    }



