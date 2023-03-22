import domain.position.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class PositionTest {
    @Test
    fun `오목알의 위치는 범위가 1부터 15인 x, y를 가지고 있다`() {
        val position = Point(10, 10)
        assertAll({
            assertThat(position.row).isEqualTo(10)
            assertThat(position.col).isEqualTo(10)
        })
    }

    @Test
    fun `x의 범위가 1부터 15 사이가 아니면 에러가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Point(16, 10)
        }
    }

    @Test
    fun `y의 범위가 1부터 15 사이가 아니면 에러가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Point(10, 16)
        }
    }
}
