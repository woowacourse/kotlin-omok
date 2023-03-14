import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class PointTest {
    @Test
    fun `y좌표로 1에서 15를 벗어나는 값을 받으면 예외가 발생한다`() {
        assertAll(
            "y좌표로 1에서 15를 벗어나는 값을 받으면 예외가 발생한다",
            { assertThrows<IllegalArgumentException> { Point(1, 16) } },
            { assertThrows<IllegalArgumentException> { Point(1, 0) } }
        )
    }

    @Test
    fun `x좌표로 1에서 15를 벗어나는 값을 받으면 예외가 발생한다`() {
        assertAll(
            "x좌표로 1에서 15를 벗어나는 값을 받으면 예외가 발생한다",
            { assertThrows<IllegalArgumentException> { Point(0, 10) } },
            { assertThrows<IllegalArgumentException> { Point(16, 10) } }
        )
    }
}
