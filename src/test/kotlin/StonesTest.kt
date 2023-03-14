import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class StonesTest {
    @Test
    fun `각각 다른 위치의 오목알을 가질 수 있다`() {
        assertDoesNotThrow {
            Stones(Stone.of(10, 10), Stone.of(11, 11))
        }
    }

    @Test
    fun `중복되는 위치의 오목알을 가지면 에러가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Stones(Stone.of(10, 10), Stone.of(10, 10))
        }
    }
}
