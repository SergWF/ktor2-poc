package wf.poc.ktor2.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<Validated> {
            it.validate()
        }
    }
}

interface Validated {
    fun validate(): ValidationResult
}