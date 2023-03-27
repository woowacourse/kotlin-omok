package omok.domain.judgment

import omok.domain.board.Board
import omok.domain.board.Column
import omok.domain.board.DOWNWARD_DIAGONAL_BOARD
import omok.domain.board.HORIZONTAL_BOARD
import omok.domain.board.NO_WINNER_BOARD
import omok.domain.board.Position
import omok.domain.board.Row
import omok.domain.board.UPWARD_DIAGONAL_BOARD
import omok.domain.board.VERTICAL_BOARD
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WinningRefereeTest {
    @Test
    fun `우상향 대각선으로 다섯개 이상의 돌이 연속한다`() {
        val board = Board(UPWARD_DIAGONAL_BOARD)

        val referee = WinningReferee().hasFiveOrMoreStoneInRow(board.positions, Position(Column.I, Row.NINE))

        assertThat(referee).isTrue
    }

    @Test
    fun `우하향 대각선으로 다섯개 이상의 돌이 연속한다`() {
        val board = Board(DOWNWARD_DIAGONAL_BOARD)

        val referee = WinningReferee().hasFiveOrMoreStoneInRow(board.positions, Position(Column.I, Row.NINE))

        assertThat(referee).isTrue
    }

    @Test
    fun `수직으로 다섯개 이상의 돌이 연속한다`() {
        val board = Board(VERTICAL_BOARD)

        val referee = WinningReferee().hasFiveOrMoreStoneInRow(board.positions, Position(Column.I, Row.NINE))

        assertThat(referee).isTrue
    }

    @Test
    fun `수평으로 다섯개 이상의 돌이 연속한다`() {
        val board = Board(HORIZONTAL_BOARD)

        val referee = WinningReferee().hasFiveOrMoreStoneInRow(board.positions, Position(Column.I, Row.NINE))

        assertThat(referee).isTrue
    }

    @Test
    fun `다섯개 이상의 돌이 연속하지 않는다`() {
        val board = Board(NO_WINNER_BOARD)

        val referee = WinningReferee().hasFiveOrMoreStoneInRow(board.positions, Position(Column.I, Row.NINE))

        assertThat(referee).isFalse
    }
}
