package omok.domain.judgment

import omok.domain.board.Board
import omok.domain.board.Column
import omok.domain.board.Position
import omok.domain.board.Row
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class RenjuRefereeTest {
    @Test
    fun `4-3-3이어도 오목이면 금지된 수가 아니다`() {
        val board = Board(FOUR_THREE_THREE_WINNING_BOARD)
        val referee = RenjuReferee()
        val position = Position(Column.D, Row.TWELVE)

        Assertions.assertThat(referee.isForbiddenPlacement(board.positions, position)).isFalse
    }

    @Test
    fun `4-4-3이어도 오목이면 금지된 수가 아니다`() {
        val board = Board(FOUR_FOUR_THREE_WINNING_BOARD)
        val referee = RenjuReferee()
        val position = Position(Column.C, Row.THIRTEEN)

        Assertions.assertThat(referee.isForbiddenPlacement(board.positions, position)).isFalse
    }
}
