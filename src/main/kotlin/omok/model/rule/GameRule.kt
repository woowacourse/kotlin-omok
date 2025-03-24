package omok.model.rule

import omok.model.board.Board
import omok.model.board.Point

interface GameRule {
    fun validateMove(
        board: Board,
        previousPoint: Point,
    ): Boolean
}
