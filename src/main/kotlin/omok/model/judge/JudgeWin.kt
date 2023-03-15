package omok.model.judge

import omok.model.Board
import omok.model.GoStone
import omok.model.state.State
import omok.model.state.Stay
import omok.model.state.Win

object JudgeWin {
    fun checkWin(goStone: GoStone, board: Board): State {
        val dx = arrayOf(-1, -1, 0, 1, 1, 1, 0, -1)
        val dy = arrayOf(0, 1, 1, 1, 0, -1, -1, -1)
        for (i in 0 until 8) {
            if (!checkDirection(goStone, dx[i], dy[i], board)) {
                return Stay(board)
            }
        }
        return Win(board)
    }

    private fun checkDirection(goStone: GoStone, dx: Int, dy: Int, board: Board): Boolean {
        val x = goStone.coordinate.x
        val y = goStone.coordinate.y
        for (i in 0 until 5) {
            val nx = x + dx * i
            val ny = y + dy * i
            if (nx < 0 || nx >= 15 || ny < 0 || ny >= 15) {
                return false
            }

            val placedStone: GoStone? = board.board[nx][ny]
            if (placedStone != null && placedStone.color != goStone.color) {
                return false
            }
        }
        return true
    }
}
