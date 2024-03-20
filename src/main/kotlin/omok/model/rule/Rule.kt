package omok.model.rule

import omok.model.Board
import omok.model.entity.StoneColor

interface Rule {
    fun check(
        board: Board,
        color: StoneColor,
    ): Boolean
}
