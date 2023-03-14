package domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class StoneTest {
    @Test
    fun `좌표를 저장한다`() {
        assertDoesNotThrow { Stone(1, 3) }
    }
}
