package omok.model.rule

import omok.model.board.Board
import omok.model.board.Point

interface OmokRule {
    fun calculate(
        board: Board,
        previousPoint: Point,
    ): Boolean
}
