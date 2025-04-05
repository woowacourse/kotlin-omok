package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardCell
import woowacourse.omok.model.board.BoardCellState
import woowacourse.omok.model.position.Position

class BoardTest {
    @Test
    fun `원하는 한 변의 길이를 받아 정사각형의 보드판을 만들 수 있다`() {
        // given:
        // when:
        val board: Board = Board(10)

        // then:
        assertThat(board.positions.size).isEqualTo(100)
    }

    @Test
    fun `특정 위치에 있는 오목판의 상태를 알 수 있다`() {
        // given:
        val position = Position(1, 2)
        val board = Board(BoardCell(position, BoardCellState.Exist(Stone.WHITE)))

        // when:
        val actual: BoardCellState = board.getBoardCell(position)

        // then:
        assertThat(actual).isEqualTo(BoardCellState.Exist(Stone.WHITE))
    }

    @Test
    fun `원하는 위치에 돌을 둘 수 있다`() {
        // given:
        val position = Position(1, 2)
        val board = Board()

        // when:
        board.put(position, Stone.WHITE)

        // then:
        assertThat(board.getBoardCell(position)).isEqualTo(BoardCellState.Exist(Stone.WHITE))
    }
}
