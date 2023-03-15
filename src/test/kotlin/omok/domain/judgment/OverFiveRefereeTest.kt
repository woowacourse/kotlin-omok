package omok.domain.judgment

import omok.domain.board.Board
import omok.domain.board.Column
import omok.domain.board.Position
import omok.domain.board.Row
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class OverFiveRefereeTest {
    @Test
    fun `장목이면 금지된 수이다`() {
        val board = Board(OVER_FIVE_BOARD)
        val referee = OverFiveReferee()
        val position = Position(Column.H, Row.EIGHT)

        Assertions.assertThat(referee.isForbiddenPlacement(board.positions, position)).isTrue
    }
}
