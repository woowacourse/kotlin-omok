package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import woowacourse.omok.model.board.BoardCell
import woowacourse.omok.model.board.BoardCellState
import woowacourse.omok.model.testDouble.POSITION_5_5

class BoardCellTest {
    @Test
    fun `오목판 위치에는 좌표와 상태를 가진다`() {
        val boardCell: BoardCell =
            BoardCell(position = POSITION_5_5, state = BoardCellState.Empty)
        assertAll({
            assertThat(boardCell.position).isEqualTo(POSITION_5_5)
            assertThat(boardCell.state).isEqualTo(BoardCellState.Empty)
        })
    }
}
