package wf.poc.ktor2.plugins

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.JwkProviderBuilder
import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import wf.poc.ktor2.domain.Email
import wf.poc.ktor2.domain.FirstName
import wf.poc.ktor2.domain.LastName
import java.net.URL
import java.util.concurrent.TimeUnit

fun Application.configureSecurity() {
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
                println("==========security credential claims===========")
                println(credential.payload.claims)
                println("audience = ${credential.payload.audience}")
                println("================================================")
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

val AuthenticationContext.user: User
    get() = User(
        email = Email(jwtPrincipal["email"] ?: throw IllegalStateException("Email can not be empty")),
        firstName = FirstName(
            jwtPrincipal["given_name"] ?: throw IllegalStateException("given_name can not be empty")
        ),
        lastName = LastName(
            jwtPrincipal["family_name"] ?: throw IllegalStateException("family_name can not be empty")
        ),
    )


