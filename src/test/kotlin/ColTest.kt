import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class ColTest {
    @Test
    fun `유효한 열이 아니라면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Col.from('Z') }
    }

    @Test
    fun `열이 유효하면 생성한다`() {
        assertDoesNotThrow { Col.from('A') }
    }

    @Test
    fun `A를 1로 계산한다`() {
        val col = Col.from('A')
        assertEquals(col.value, 1)
    }
}
