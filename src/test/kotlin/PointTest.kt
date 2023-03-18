import domain.stone.Point
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class PointTest {

    @Test
    fun `x의 범위가 0이상 14이하가 되지 않는 경우 예외를 던진다`() {
        val x = 15
        val y = 10

        assertThrows<IllegalArgumentException> {
            Point(x, y)
        }
    }

    @Test
    fun `x의 범위는 0이상 14이다`() {
        val x = 13
        val y = 10

        assertDoesNotThrow {
            Point(x, y)
        }
    }

    @Test
    fun `y의 범위가 0이상 14이하가 되지 않는 경우 예외를 던진다`() {
        val x = 13
        val y = 18

        assertThrows<IllegalArgumentException> {
            Point(x, y)
        }
    }

    @Test
    fun `y의 범위는 0이상 14이다`() {
        val x = 12
        val y = 14

        assertDoesNotThrow {
            Point(x, y)
        }
    }
}
