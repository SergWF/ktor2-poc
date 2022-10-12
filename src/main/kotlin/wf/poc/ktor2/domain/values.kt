package wf.poc.ktor2.domain

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Email(val value: String) {
    init {
        if (value.isNullOrBlank()) {
            throw IllegalStateException("Email can not be empty")
        }
    }
}

@Serializable
@JvmInline
value class FirstName(val value: String) {
    init {
        if (value.isNullOrBlank()) {
            throw IllegalStateException("FirstName can not be empty")
        }
    }
}

@Serializable
@JvmInline
value class LastName(val value: String) {
    init {
        if (value.isNullOrBlank()) {
            throw IllegalStateException("LastName can not be empty")
        }
    }
}

