package omok.model.rule

import omok.model.Board
import omok.model.entity.Stone

object OutOfBoardRule : Rule {
    override fun check(
        stone: Stone,
        board: Board,
    ): Boolean {
        return !board.isPointInBoard(stone.point)
    }
}
