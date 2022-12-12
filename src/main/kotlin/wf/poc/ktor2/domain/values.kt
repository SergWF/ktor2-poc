package wf.poc.ktor2.domain

import kotlinx.serialization.Serializable
val emailRegex = Regex(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)
@Serializable
@JvmInline
value class Email(val value: String) {
    init {
        if (value.isNullOrBlank()) {
            throw IllegalArgumentException("Email can't be empty")
        }
        if(!emailRegex.matches(value)) {
            throw IllegalArgumentException("Email should match email pattern")
        }
    }
}

@Serializable
@JvmInline
value class FirstName(val value: String) {
    init {
        if (value.isNullOrBlank()) {
            throw IllegalStateException("FirstName can't be empty")
        }
    }
}

@Serializable
@JvmInline
value class LastName(val value: String) {
    init {
        if (value.isNullOrBlank()) {
            throw IllegalStateException("LastName can't be empty")
        }
    }
}

