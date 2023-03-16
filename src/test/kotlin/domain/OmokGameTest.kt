package domain

import domain.board.Board
import org.junit.jupiter.api.Test

class OmokGameTest {
    @Test
    fun `흑돌이 오목을 완성하면 게임이 종료된다`() {
        val omokGame = OmokGame()
        val black = (1..5).map { Stone(it, 0) }.toMutableList()
        val white = (1..5).map { Stone(it, 3) }.toMutableList()
        val turn = mutableListOf<Stone>()
        (1..5).forEach {
            turn.add(black.removeFirst())
            turn.add(white.removeFirst())
        }
        var index = 0
        omokGame.runGame({ turn[index++] }, ::onMove, {}, {})
    }

    private fun onMove(board: Board, state: State, stone: Stone) {}
}
