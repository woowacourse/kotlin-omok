package domain

import domain.turn.BlackTurn
import domain.turn.WhiteTurn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `1,1위치에 흑돌이 놓여있으면 놓을 수 없다`() {
        // given
        val stone = Stone(1, 1)
        val blackTurn = BlackTurn()
        val whiteTurn = WhiteTurn()
        val board = Board(blackTurn, whiteTurn)
        blackTurn.move(stone)

        // when
        val actual = board.canMove(stone)

        // then
        assertThat(actual).isFalse
    }
}
