package wf.poc.ktor2.plugins

import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.interfaces.Payload
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTConfigureFunction
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import wf.poc.ktor2.plugins.AuthenticationGroup.POWER_USER
import wf.poc.ktor2.plugins.AuthenticationGroup.REGULAR_USER
import java.net.URL
import java.util.concurrent.TimeUnit

fun Application.configureSecurity() {

    val jwtIssuer = environment.config.property("keycloak-jwt.issuer").getString()
    val jwtJwkUrl = environment.config.property("keycloak-jwt.jwk-url").getString()
    val jwtAudience = environment.config.property("keycloak-jwt.audience").getString()
    val jwtUserRole = environment.config.property("keycloak-jwt.user-role").getString()
    val jwtPowerUserRole = environment.config.property("keycloak-jwt.power-user-role").getString()
    val jwtRealm = environment.config.property("keycloak-jwt.realm").getString()
    val jwkProvider = JwkProviderBuilder(URL(jwtJwkUrl))
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    val verifierConfigFun: JWTConfigureFunction = {
        acceptLeeway(3)
        withAudience(jwtAudience)
        withIssuer(jwtIssuer)
    }

    authentication {
        jwt(REGULAR_USER.name) {
            realm = jwtRealm
            verifier(jwkProvider, jwtIssuer, verifierConfigFun)
            validate { credential ->
                if (credential.payload.hasReamRole(jwtUserRole)) JWTPrincipal(credential.payload) else null
            }
        }
        jwt(POWER_USER.name) {
            realm = jwtRealm
            verifier(jwkProvider, jwtIssuer, verifierConfigFun)
            validate { credential ->
                if (credential.payload.hasReamRole(jwtPowerUserRole)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}

enum class AuthenticationGroup {
    REGULAR_USER,
    POWER_USER
}

fun Payload.hasReamRole(roleName: String): Boolean =
    getRealmRoles().map { it.lowercase() }.contains(roleName.lowercase())

fun Payload.hasClientRole(clientName: String, roleName: String): Boolean =
    getClientRoles(clientName).map { it.lowercase() }.contains(roleName.lowercase())

fun Payload.hasScope(scope: String): Boolean =
    getClaim("scope").asString().split(" ").contains(scope)

fun Payload.getPrincipal(): JWTPrincipal = JWTPrincipal(this)

@Suppress("UNCHECKED_CAST")
fun Payload.getRealmRoles(): List<String> =
    getClaim("realm_access")?.asMap()?.get("roles") as? List<String> ?: emptyList()

@Suppress("UNCHECKED_CAST")
fun Payload.getClientRoles(clientName: String): List<String> =
    (getClaim("resource_access")?.asMap()?.get(clientName) as? Map<String, List<String>>)?.get("roles") ?: emptyList()
