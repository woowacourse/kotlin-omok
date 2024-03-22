@file:Suppress("ktlint:standard:no-wildcard-imports")

package omok.model

import omok.model.board.CoordsNumber
import omok.model.board.Position
import omok.model.board.Stone
import omok.model.omokGame.Board
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun `게임 상태가 실행 중인지 확인`() {
        assertTrue(board.isRunning())
    }

    @Test
    fun `돌을 올바르게 놓는 경우`() {
        val x = CoordsNumber(0)
        val y = CoordsNumber(0)
        board.setStone(x, y, Stone.BLACK)
        assertEquals(Stone.BLACK, board.gameBoard[y.number][x.number])
    }

    @Test
    fun `돌을 놓은 위치가 비어 있지 않은 경우 확인`() {
        val x = CoordsNumber(0)
        val y = CoordsNumber(0)
        board.setStone(x, y, Stone.WHITE)
        assertTrue(board.isNotEmpty(x, y))
    }

    @Test
    fun `금지된 위치에 돌을 놓으려는 경우 확인`() {
        val forbiddenPositions = listOf(Position(CoordsNumber(0), CoordsNumber(1)))
        assertTrue(board.isPositionForbidden(CoordsNumber(1), CoordsNumber(0), forbiddenPositions))
    }

    @Test
    fun `돌을 놓은 후 게임이 종료되는 경우 확인`() {
        for (i in 0 until 4) {
            board.setStone(CoordsNumber(i), CoordsNumber(0), Stone.BLACK)
        }
        board.setStone(CoordsNumber(4), CoordsNumber(0), Stone.BLACK)
        board.isGameOver(CoordsNumber(4), CoordsNumber(0), Stone.BLACK)
        assertEquals(board.isRunning(), false) // 게임이 끝났는지 확인
    }

    @Test
    fun `33 금수 확인`() {
        for (i in 2 until 4) {
            board.setStone(CoordsNumber(i), CoordsNumber(1), Stone.BLACK)
        }
        for (j in 2 until 4) {
            board.setStone(CoordsNumber(1), CoordsNumber(j), Stone.BLACK)
        }
        val forbids = board.findForbiddenPositions(Stone.BLACK)
        Assertions.assertThat(forbids).isEqualTo(listOf(Position(CoordsNumber(1), CoordsNumber(1))))
    }

    @Test
    fun `43은 금수아니다`() {
        for (i in 2 until 5) {
            board.setStone(CoordsNumber(i), CoordsNumber(1), Stone.BLACK)
        }
        for (j in 2 until 4) {
            board.setStone(CoordsNumber(1), CoordsNumber(j), Stone.BLACK)
        }
        val forbids = board.findForbiddenPositions(Stone.BLACK)
        Assertions.assertThat(forbids.size).isEqualTo(0)
    }

    @Test
    fun `44는 한쪽이 막혀있어도 금수다`() {
        for (i in 2 until 5) {
            board.setStone(CoordsNumber(i), CoordsNumber(1), Stone.BLACK)
        }
        for (j in 2 until 5) {
            board.setStone(CoordsNumber(1), CoordsNumber(j), Stone.BLACK)
        }
        board.setStone(CoordsNumber(1), CoordsNumber(5), Stone.WHITE)
        val forbids = board.findForbiddenPositions(Stone.BLACK)
        Assertions.assertThat(forbids).isEqualTo(listOf(Position(CoordsNumber(1), CoordsNumber(1))))
    }

    @Test
    fun `두쪽이 다 막힌 44는 금수아니다`() {
        for (i in 2 until 5) {
            board.setStone(CoordsNumber(i), CoordsNumber(1), Stone.BLACK)
        }
        for (j in 2 until 5) {
            board.setStone(CoordsNumber(1), CoordsNumber(j), Stone.BLACK)
        }
        board.setStone(CoordsNumber(1), CoordsNumber(5), Stone.WHITE)
        board.setStone(CoordsNumber(1), CoordsNumber(1), Stone.WHITE)
        val forbids = board.findForbiddenPositions(Stone.BLACK)
        Assertions.assertThat(forbids.size).isEqualTo(0)
    }

    @Test
    fun `금수때문에 못 놓는 수는 33 금수로 취급하지 않는다`() {
        board.gameBoard[8][7] = Stone.BLACK
        board.gameBoard[6][5] = Stone.BLACK
        board.gameBoard[6][7] = Stone.BLACK
        board.gameBoard[7][7] = Stone.BLACK
        board.gameBoard[8][7] = Stone.BLACK
        board.gameBoard[9][8] = Stone.BLACK
        board.gameBoard[10][9] = Stone.BLACK
        board.gameBoard[12][8] = Stone.BLACK
        board.gameBoard[9][10] = Stone.BLACK
        board.gameBoard[12][9] = Stone.BLACK
        board.gameBoard[10][8] = Stone.BLACK
        board.gameBoard[8][6] = Stone.BLACK
        board.gameBoard[13][7] = Stone.BLACK
        board.gameBoard[5][5] = Stone.BLACK
        board.gameBoard[7][8] = Stone.BLACK

        val forbids = board.findForbiddenPositions(Stone.BLACK)
        Assertions.assertThat(Position(CoordsNumber(7), CoordsNumber(5)) in forbids).isFalse()
    }
}
