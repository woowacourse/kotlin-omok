package omok.model.rule

import omok.model.Board

interface Rule {
    fun check(board: Board): Boolean
}
