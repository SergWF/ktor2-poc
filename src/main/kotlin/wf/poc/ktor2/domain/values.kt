package wf.poc.ktor2.domain

@kotlinx.serialization.Serializable
@JvmInline
value class Email(private val value: String) {
    init {
        if(value.isNullOrBlank()) {
            throw IllegalStateException("Email can not be empty")
        }
    }
}

@kotlinx.serialization.Serializable
@JvmInline
value class FirstName(private val value: String) {
    init {
        if(value.isNullOrBlank()) {
            throw IllegalStateException("FirstName can not be empty")
        }
    }
}

@kotlinx.serialization.Serializable
@JvmInline
value class LastName(private val value: String) {
    init {
        if(value.isNullOrBlank()) {
            throw IllegalStateException("LastName can not be empty")
        }
    }
}

