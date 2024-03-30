package omok.model.rule

import omok.model.Board
import omok.model.entity.Stone

interface Rule {
    fun check(
        stone: Stone,
        board: Board,
    ): Boolean

    companion object {
        val directions =
            listOf(
                Direction(1, 0),
                Direction(0, 1),
                Direction(1, 1),
                Direction(-1, 1),
            )
    }
}
