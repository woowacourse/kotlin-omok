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

    @Test
    fun `화이트 턴의 1,1위치에 착수한다`() {
        // given
        val stone = Stone(1, 1)
        val blackTurn = BlackTurn()
        val whiteTurn = WhiteTurn()
        val board = Board(blackTurn, whiteTurn)

        // when
        board.moveWhite(stone)

        // then
        assertThat(board.whiteTurn.isEmpty(stone)).isFalse
        assertThat(board.blackTurn.isEmpty(stone)).isTrue
    }

    @Test
    fun `블랙 턴의 1,1위치에 착수한다`() {
        // given
        val stone = Stone(1, 1)
        val blackTurn = BlackTurn()
        val whiteTurn = WhiteTurn()
        val board = Board(blackTurn, whiteTurn)

        // when
        board.moveBlack(stone)

        // then
        assertThat(board.whiteTurn.isEmpty(stone)).isTrue
        assertThat(board.blackTurn.isEmpty(stone)).isFalse
    }
}
