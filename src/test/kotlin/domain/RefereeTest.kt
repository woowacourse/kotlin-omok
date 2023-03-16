package domain

import domain.board.Board
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RefereeTest {
    @Test
    fun `가로로 연속 5개 놓여있으면 승리다`() {
        // given
        val referee = Referee()
        val board = Board()
        (1..5).forEach { column ->
            board.move(Stone(1, column), State.BLACK)
        }

        // when, then
        assertThat(referee.isWin(board, State.BLACK)).isTrue
    }

    @Test
    fun `세로로 연속 5개 놓여있으면 승리다`() {
        // given
        val referee = Referee()
        val board = Board()
        (1..5).forEach { row ->
            board.move(Stone(row, 1), State.BLACK)
        }

        // when, then
        assertThat(referee.isWin(board, State.BLACK)).isTrue
    }

    @Test
    fun `대각선으로 5개 놓여있으면 승리다`() {
        // given
        val referee = Referee()
        val board = Board()
        (1..5).forEach { index ->
            board.move(Stone(index, index), State.BLACK)
        }

        // when, then
        assertThat(referee.isWin(board, State.BLACK)).isTrue
    }

    @Test
    fun `가로로 연속 4개 놓여있으면 승리가 아니다`() {
        // given
        val referee = Referee()
        val board = Board()
        (1..4).forEach { column ->
            board.move(Stone(1, column), State.BLACK)
        }

        // when, then
        assertThat(referee.isWin(board, State.BLACK)).isFalse
    }
}
