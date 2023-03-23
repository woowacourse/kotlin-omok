package library.combinerule

import domain.domain.CoordinateState
import domain.domain.Position
import domain.domain.BoardState
import domain.library.combinerule.CombinedRuleAdapter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CombinedRuleAdapterTest {

    @Test
    fun `흑의 승리조건일시 True를 반환한다`() {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[4][3] = CoordinateState.BLACK
        board[4][4] = CoordinateState.BLACK
        board[4][5] = CoordinateState.BLACK
        board[4][7] = CoordinateState.BLACK
        val targetCoordinate = Position(4, 6)

        assertThat(CombinedRuleAdapter().isBlackWin(targetCoordinate, BoardState(board))).isTrue
    }

    @Test
    fun `백의 승리조건일시 True를 반환한다`() {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[4][3] = CoordinateState.WHITE
        board[4][4] = CoordinateState.WHITE
        board[4][5] = CoordinateState.WHITE
        board[4][7] = CoordinateState.WHITE
        val targetCoordinate = Position(4, 6)

        assertThat(CombinedRuleAdapter().isWhiteWin(targetCoordinate, BoardState(board))).isTrue
    }

    @Test
    fun `금수가 아니고 놓을곳이 비어있다면 True를 반환한다`() {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[4][3] = CoordinateState.WHITE
        board[4][4] = CoordinateState.WHITE

        val targetCoordinate = Position(4, 6)

        assertThat(
            CombinedRuleAdapter().checkAddablePosition(
                BoardState(board),
                CoordinateState.WHITE,
                targetCoordinate,
            ),
        ).isTrue
    }
}
