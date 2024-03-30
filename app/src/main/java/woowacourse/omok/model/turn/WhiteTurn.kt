package omok.model.turn

import omok.model.Board
import omok.model.entity.StoneColor
import java.lang.IllegalStateException

class WhiteTurn(board: Board) : Turn(board) {
    override fun destination(board: Board): Turn {
        val stone = board.previousStone() ?: throw IllegalStateException()
        if (isWin(stone) || isDraw()) {
            return Finished(board)
        }
        return BlackTurn(board)
    }

    override fun isWin(): Boolean = false

    override fun color(): StoneColor {
        return StoneColor.WHITE
    }
}
