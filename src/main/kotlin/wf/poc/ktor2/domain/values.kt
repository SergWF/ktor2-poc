package wf.poc.ktor2.domain

@kotlinx.serialization.Serializable
@JvmInline
value class Email(private val value: String)

@kotlinx.serialization.Serializable
@JvmInline
value class FirstName(private val value: String)

@kotlinx.serialization.Serializable
@JvmInline
value class LastName(private val value: String)
