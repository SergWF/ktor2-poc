package wf.poc.ktor2.plugins

import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.Payload
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SecurityKtTest {

    private val payload: Payload = mockk()
    private val claim: Claim = mockk()

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun hasReamRole() {
        every { claim.asMap() } returns mapOf("roles" to listOf("role1", "role2"))
        every { payload.getClaim("realm_access") } returns claim
        assertTrue(payload.hasReamRole("role1"))
        assertFalse(payload.hasReamRole("wrong-role"))
    }

    @Test
    fun hasClientRole() {
        val client = "client"
        val clientRole = "client-role1"
        every { claim.asMap() } returns mapOf(client to mapOf("roles" to listOf(clientRole, "client-role2")))
        every { payload.getClaim("resource_access") } returns claim
        assertTrue(payload.hasClientRole(client, clientRole))
        assertFalse(payload.hasClientRole(client, "wrong-role"))
        assertFalse(payload.hasClientRole("wrong-client", clientRole))
    }

    @Test
    fun hasScope() {
        val scope = "scope2"
        every { claim.asString() } returns "scope1 scope2 scope3"
        every { payload.getClaim("scope") } returns claim
        assertTrue(payload.hasScope(scope))
        assertFalse(payload.hasScope("wrong-scope"))
    }
}