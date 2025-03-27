package omok.model

import omok.model.entity.Stone
import omok.model.entity.board.Board
import omok.model.entity.board.BoardPosition
import omok.model.entity.board.BoardPositionState
import omok.model.entity.board.DefaultBoard
import omok.model.entity.board.DefaultBoardPosition
import omok.model.entity.position.DefaultPosition
import omok.model.entity.position.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `원하는 한 변의 길이를 받아 정사각형의 보드판을 만들 수 있다`() {
        // given:
        // when:
        val board: Board = DefaultBoard(10)

        // then:
        assertThat(board.positions.size).isEqualTo(100)
    }

    @Test
    fun `특정 위치에 있는 오목판의 상태를 알 수 있다`() {
        // given:
        val position: Position = DefaultPosition(1, 2)
        val board: Board = DefaultBoard(DefaultBoardPosition(position, BoardPositionState.Exist.White))

        // when:
        val actual: BoardPositionState = board.stateAt(position)

        // then:
        assertThat(actual).isEqualTo(BoardPositionState.Exist.White)
    }

    @Test
    fun `원하는 위치에 돌을 둘 수 있다`() {
        // given:
        val position: Position = DefaultPosition(1, 2)
        val boardPosition: BoardPosition = DefaultBoardPosition(position)
        val board: Board = DefaultBoard(boardPosition)

        // when:
        board.put(position, Stone.WHITE)

        // then:
        assertThat(board.stateAt(position)).isEqualTo(BoardPositionState.Exist.White)
    }
}
