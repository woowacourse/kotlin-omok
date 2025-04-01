package omok.model

import omok.model.entity.board.BoardPosition
import omok.model.entity.board.BoardPositionState
import omok.model.entity.board.DefaultBoardPosition
import omok.model.testDouble.FakePosition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class BoardPositionTest {
    @Test
    fun `오목판 위치에는 좌표와 상태를 가진다`() {
        val boardPosition: BoardPosition =
            DefaultBoardPosition(position = FakePosition(), state = BoardPositionState.Empty)
        assertAll({
            assertThat(boardPosition.position).isEqualTo(FakePosition())
            assertThat(boardPosition.state).isEqualTo(BoardPositionState.Empty)
        })
    }
}
