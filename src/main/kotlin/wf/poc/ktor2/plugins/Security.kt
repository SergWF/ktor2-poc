package wf.poc.ktor2.plugins

import com.auth0.jwk.JwkProviderBuilder
import io.ktor.server.application.Application
import io.ktor.server.auth.AuthenticationContext
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import org.slf4j.LoggerFactory
import wf.poc.ktor2.domain.Email
import wf.poc.ktor2.domain.FirstName
import wf.poc.ktor2.domain.LastName
import java.net.URL
import java.util.concurrent.TimeUnit

fun Application.configureSecurity() {

    val logger = LoggerFactory.getLogger("Application.configureSecurity")
    val jwtIssuer = this@configureSecurity.environment.config.property("keycloak-jwt.issuer").getString()
    val jwtJwkUrl = this@configureSecurity.environment.config.property("keycloak-jwt.jwk-url").getString()
    val jwtAudience = this@configureSecurity.environment.config.property("keycloak-jwt.audience").getString()
    val jwtRealm = this@configureSecurity.environment.config.property("keycloak-jwt.realm").getString()
    val jwkProvider = JwkProviderBuilder(URL(jwtJwkUrl))
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    authentication {
        jwt("keycloak") {
            realm = jwtRealm
            verifier(jwkProvider, jwtIssuer) {
                acceptLeeway(3)
            }
            validate { credential ->
                logger.debug("==========security credential claims===========")
                logger.debug("${credential.payload.claims}")
                logger.debug("audience = ${credential.payload.audience}")
                logger.debug("================================================")
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}


data class User(
    val email: Email,
    val firstName: FirstName,
    val lastName: LastName
)

private val AuthenticationContext.jwtPrincipal: JWTPrincipal
    get() = this.principal as JWTPrincipal

private fun JWTPrincipal.getNotNull(key: String): String =
    this[key] ?: throw IllegalStateException("$key can not be empty")

val AuthenticationContext.user: User
    get() = User(
        email = Email(jwtPrincipal.getNotNull("email")),
        firstName = FirstName(jwtPrincipal.getNotNull("given_name")),
        lastName = LastName(jwtPrincipal.getNotNull("family_name"))
    )


