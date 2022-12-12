package wf.poc.ktor2.plugins

import io.konform.validation.Invalid
import io.konform.validation.Valid
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<Validatable> {
            it.validate()
        }
    }
}

interface Validatable {
    fun validate(): ValidationResult
}

fun <T : Validatable> io.konform.validation.ValidationResult<T>.toKtor(): ValidationResult =
    when (this) {
        is Valid<T> -> ValidationResult.Valid
        is Invalid<T> -> ValidationResult.Invalid(this.errors.map { "${it.dataPath} -> ${it.message}" })
    }

