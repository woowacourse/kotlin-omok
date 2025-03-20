package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rule.wrapper.point.Point
import java.lang.IllegalArgumentException

class BlackPlayerTest {
    @Test
    fun `흑은 이미 돌이 있는 자리인지 알 수 있다`() {
        val blackPlayer = BlackPlayer().apply { place(Point(1, 1), emptyList()) }
        val actual: Boolean = blackPlayer.isOccupied(Point(1, 1), emptyList())

        val expected = true

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑은 돌을 둘 수 있다`() {
        val blackPlayer = BlackPlayer().apply { place(Point(1, 1), emptyList()) }

        val actual = blackPlayer.points

        val expected = listOf(Point(1, 1))

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑은 삼삼 위치에 돌을 둘 수 없다`() {
        val blackPlayer =
            BlackPlayer().apply {
                place(Point(4, 4), emptyList())
                place(Point(5, 4), emptyList())
                place(Point(6, 5), emptyList())
                place(Point(5, 6), emptyList())
            }

        assertThrows<IllegalArgumentException> { blackPlayer.place(Point(7, 4), emptyList()) }
    }

    @Test
    fun `흑은 사사 위치에 돌을 둘 수 없다`() {
        val blackPlayer =
            BlackPlayer().apply {
                place(Point(12, 3), emptyList())
                place(Point(12, 4), emptyList())
                place(Point(12, 9), emptyList())
                place(Point(12, 10), emptyList())
                place(Point(12, 7), emptyList())
            }

        assertThrows<IllegalArgumentException> { blackPlayer.place(Point(12, 6), emptyList()) }
    }

    @Test
    fun `흑은 장목을 둘 수 없다`() {
        val blackPlayer =
            BlackPlayer().apply {
                place(Point(15, 3), emptyList())
                place(Point(14, 3), emptyList())
                place(Point(12, 3), emptyList())
                place(Point(11, 3), emptyList())
                place(Point(10, 3), emptyList())
            }

        assertThrows<IllegalArgumentException> { blackPlayer.place(Point(13, 3), emptyList()) }
    }
}
