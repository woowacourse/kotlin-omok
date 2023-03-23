package domain

import domain.domain.*
import domain.library.combinerule.CombinedRuleAdapter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {

    @Test
    fun `승자가 있다면 END를 반환한다 (흑승)`() {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[3][4] = CoordinateState.BLACK
        board[3][5] = CoordinateState.BLACK
        board[3][6] = CoordinateState.BLACK
        board[3][7] = CoordinateState.BLACK
        val targetCoordinate = Position(3, 8)
        val gumsuGame = OmokGame(board = Board(BoardState(board)), gameRule = CombinedRuleAdapter())

        assertThat(gumsuGame.progressTurn(targetCoordinate)).isEqualTo(ProgressState.END)
    }

    @Test
    fun `승자가 있다면 END를 반환한다 (백승)`() {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[3][4] = CoordinateState.WHITE
        board[3][5] = CoordinateState.WHITE
        board[3][6] = CoordinateState.WHITE
        board[3][7] = CoordinateState.WHITE
        val targetCoordinate = Position(3, 8)
        val gumsuGame = OmokGame(board = Board(BoardState(board)), initTurn = CoordinateState.WHITE, gameRule = CombinedRuleAdapter())

        assertThat(gumsuGame.progressTurn(targetCoordinate)).isEqualTo(ProgressState.END)
    }
    @Test
    fun `흑이 금수를두면 ERROR를 반환한다`() {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[3][4] = CoordinateState.BLACK
        board[3][5] = CoordinateState.BLACK
        board[3][6] = CoordinateState.BLACK
        board[4][3] = CoordinateState.BLACK
        board[5][3] = CoordinateState.BLACK
        board[6][3] = CoordinateState.BLACK
        val targetCoordinate = Position(3, 3)
        val gumsuGame = OmokGame(board = Board(BoardState(board)), gameRule = CombinedRuleAdapter())

        assertThat(gumsuGame.progressTurn(targetCoordinate)).isEqualTo(ProgressState.ERROR)
    }

    @Test
    fun `승자가 없고 금수도 아니라면 있다면 CONTINUE를 반환한다`() {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[3][4] = CoordinateState.BLACK
        board[3][7] = CoordinateState.BLACK
        val targetCoordinate = Position(3, 8)
        val gumsuGame = OmokGame(board = Board(BoardState(board)), gameRule = CombinedRuleAdapter())

        assertThat(gumsuGame.progressTurn(targetCoordinate)).isEqualTo(ProgressState.CONTINUE)
    }
}
