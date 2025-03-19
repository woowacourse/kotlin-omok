package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import rule.wrapper.point.Point

class WhitePlayerTest {
    @Test
    fun `백은 이미 돌이 있는 자리인지 알 수 있다`() {
        val whitePlayer = WhitePlayer().apply { place(Point(1, 1), emptyList()) }
        val actual: Boolean = whitePlayer.isOccupied(Point(1, 1), emptyList())

        val expected = true

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `백은 돌을 둘 수 있다`() {
        val whitePlayer = WhitePlayer()
        whitePlayer.place(Point(1, 1), emptyList())

        val actual = whitePlayer.points

        val expected = listOf(Point(1, 1))

        assertThat(actual).isEqualTo(expected)
    }
}
