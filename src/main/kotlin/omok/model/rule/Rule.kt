package omok.model.rule

import omok.model.Board

interface Rule {
    fun check(board: Board): Boolean

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
