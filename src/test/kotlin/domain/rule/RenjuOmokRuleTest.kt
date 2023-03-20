package domain.rule

import domain.stone.Board
import domain.stone.Stone
import domain.stone.StonePosition
import domain.stone.StoneType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RenjuOmokRuleTest {

    @Test
    fun `5개이상의 돌이 가로로 이어져 있으면 오목조건을 충족한다`() {
        val renjuOmokRule: RenjuOmokRule = RenjuOmokRule()
        val board: Board = Board()
        board.putStone(Stone(StonePosition.from(2, 1)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(3, 1)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(4, 1)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(5, 1)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(6, 1)!!, StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(6, 1)!!, StoneType.BLACK)

        assertThat(renjuOmokRule.isWinCondition(board.board, stone)).isTrue
    }

    @Test
    fun `5개이상의 돌이 세로로 이어져 있으면 오목조건을 충족한다`() {
        val renjuOmokRule: RenjuOmokRule = RenjuOmokRule()
        val board: Board = Board()
        board.putStone(Stone(StonePosition.from(1, 1)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(1, 2)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(1, 3)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(1, 4)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(1, 5)!!, StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(1, 5)!!, StoneType.BLACK)

        assertThat(renjuOmokRule.isWinCondition(board.board, stone)).isTrue
    }

    @Test
    fun `5개이상의 돌이 오른쪽 대각선으로 이어져 있으면 오목조건을 충족한다`() {
        val renjuOmokRule: RenjuOmokRule = RenjuOmokRule()
        val board: Board = Board()
        board.putStone(Stone(StonePosition.from(1, 1)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(2, 2)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(3, 3)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(4, 4)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(5, 5)!!, StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(5, 5)!!, StoneType.BLACK)

        assertThat(renjuOmokRule.isWinCondition(board.board, stone)).isTrue
    }

    @Test
    fun `5개이상의 돌이 왼쪽 대각선으로 이어져 있으면 오목조건을 충족한다`() {
        val renjuOmokRule: RenjuOmokRule = RenjuOmokRule()
        val board: Board = Board()
        board.putStone(Stone(StonePosition.from(1, 5)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(2, 4)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(3, 3)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(4, 2)!!, StoneType.BLACK))
        board.putStone(Stone(StonePosition.from(5, 1)!!, StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(5, 1)!!, StoneType.BLACK)

        assertThat(renjuOmokRule.isWinCondition(board.board, stone)).isTrue
    }
}
