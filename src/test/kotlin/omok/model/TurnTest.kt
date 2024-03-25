package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TurnTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board(15)
    }

    @Test
    fun `흑돌 차례 다음은 백돌 차례이다`() {
        val turn = BlackTurn(BlackRule())
        val actual = turn.nextTurn(Point(0, 0), board)

        assertThat(actual).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `백돌 차례 다음은 흑돌 차례이다`() {
        val turn = WhiteTurn(WhiteRule())
        val actual = turn.nextTurn(Point(0, 0), board)

        assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }
}
