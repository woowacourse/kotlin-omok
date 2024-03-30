package omok.model.turn

import omok.model.Board
import omok.model.entity.StoneColor

class Finished(board: Board) : Turn(board) {
    val stone = board.previousStone() ?: throw IllegalStateException()

    override fun color(): StoneColor {
        return stone.stoneColor
    }

    override fun destination(board: Board): Turn {
        throw IllegalStateException()
    }

    override fun isWin(): Boolean = isWin(stone)
}
