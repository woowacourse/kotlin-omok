package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rule.wrapper.point.Point
import java.lang.IllegalArgumentException

class BlackPlayerTest {
    @Test
    fun `흑은 이미 돌이 있는 자리인지 알 수 있다`() {
        val blackPlayer = BlackPlayer().apply { place(Point(1, 1), Points()) }
        val actual: Boolean = blackPlayer.isOccupied(Point(1, 1), Points())

        val expected = true

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑은 돌을 둘 수 있다`() {
        val blackPlayer = BlackPlayer().apply { place(Point(1, 1), Points()) }

        val actual = blackPlayer.points.points

        val expected = listOf(Point(1, 1))

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑은 삼삼 위치에 돌을 둘 수 없다`() {
        val blackPlayer =
            BlackPlayer().apply {
                place(Point(4, 4), Points())
                place(Point(5, 4), Points())
                place(Point(6, 5), Points())
                place(Point(5, 6), Points())
            }

        assertThrows<IllegalArgumentException> { blackPlayer.place(Point(7, 4), Points()) }
    }

    @Test
    fun `흑은 사사 위치에 돌을 둘 수 없다`() {
        val blackPlayer =
            BlackPlayer().apply {
                place(Point(12, 3), Points())
                place(Point(12, 4), Points())
                place(Point(12, 9), Points())
                place(Point(12, 10), Points())
                place(Point(12, 7), Points())
            }

        assertThrows<IllegalArgumentException> { blackPlayer.place(Point(12, 6), Points()) }
    }

    @Test
    fun `흑은 장목을 둘 수 없다`() {
        val blackPlayer =
            BlackPlayer().apply {
                place(Point(15, 3), Points())
                place(Point(14, 3), Points())
                place(Point(12, 3), Points())
                place(Point(11, 3), Points())
                place(Point(10, 3), Points())
            }

        assertThrows<IllegalArgumentException> { blackPlayer.place(Point(13, 3), Points()) }
    }
}
