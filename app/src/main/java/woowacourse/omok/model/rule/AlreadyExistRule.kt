package omok.model.rule

import omok.model.Board
import omok.model.entity.Stone

object AlreadyExistRule : Rule {
    override fun check(
        stone: Stone,
        board: Board,
    ): Boolean {
        return !board.isPointEmpty(stone.point)
    }
}
