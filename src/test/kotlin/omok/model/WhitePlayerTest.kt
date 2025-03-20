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

    @Test
    fun `가로로 완성된 오목을 확인할 수 있다`() {
        val whitePlayer = WhitePlayer().apply {
            place(Point(1, 1), emptyList())
            place(Point(1, 2), emptyList())
            place(Point(1, 3), emptyList())
            place(Point(1, 4), emptyList())
        }
        val actual: GameState = whitePlayer.place(Point(1, 5), emptyList())

        val expected: GameState = GameState.WHITE_OMOK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `대각선으로 완성된 오목을 확인할 수 있다`() {
        val whitePlayer = WhitePlayer().apply {
            place(Point(13, 5), emptyList())
            place(Point(11, 7), emptyList())
            place(Point(10, 8), emptyList())
            place(Point(9, 9), emptyList())
        }
        val actual: GameState = whitePlayer.place(Point(12, 6), emptyList())

        val expected: GameState = GameState.WHITE_OMOK

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `오목이 완성되지 않았으면 진행 중인 상태이다`() {
        val whitePlayer = WhitePlayer().apply {
            place(Point(8, 8), emptyList())
            place(Point(8, 9), emptyList())
            place(Point(9, 8), emptyList())
        }
        val actual: GameState = whitePlayer.place(Point(9, 9), emptyList())

        val expected: GameState = GameState.PLAYING

        assertThat(actual).isEqualTo(expected)
    }
}
