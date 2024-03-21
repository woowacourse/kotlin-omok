package omok.model.rule.ban

import omok.model.Board
import omok.model.Position

interface ForbiddenPlace {
    fun availablePosition(board: Board, position: Position): Boolean
}
