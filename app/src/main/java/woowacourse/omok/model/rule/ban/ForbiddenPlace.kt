package omok.model.rule.ban

import omok.model.board.Board
import omok.model.board.Position

interface ForbiddenPlace {
    fun availablePosition(
        board: Board,
        position: Position,
    ): Boolean
}
