package omok.model.rule

import omok.model.board.Board

interface Rule {
    fun check(board: Board): Boolean
}
