package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import rule.wrapper.point.Point

class WhitePlayerTest {
    @Test
    fun `백은 이미 돌이 있는 자리인지 알 수 있다`() {
        val whitePlayer = WhitePlayer(listOf(Point(1, 1)))
        val actual: Boolean = whitePlayer.isOccupied(Point(1, 1), emptyList())

        val expected = true

        assertThat(actual).isEqualTo(expected)
    }
}
