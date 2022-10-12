package wf.poc.ktor2.route

import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.AuthenticationContext
import io.ktor.server.auth.AuthenticationProvider
import io.ktor.server.auth.Principal

class TestAuthenticationProvider internal constructor(config: Config) : AuthenticationProvider(config) {
    private val principal: Principal = config.principal

    class Config internal constructor(name: String?) : AuthenticationProvider.Config(name) {
        lateinit var principal: Principal
        internal fun build() = TestAuthenticationProvider(this)
    }

    override suspend fun onAuthenticate(context: AuthenticationContext) {
        context.principal(principal)
    }
}

fun AuthenticationConfig.test(
    name: String? = null,
    configure: TestAuthenticationProvider.Config.() -> Unit
) {
    val provider = TestAuthenticationProvider.Config(name).apply(configure).build()
    register(provider)
}
