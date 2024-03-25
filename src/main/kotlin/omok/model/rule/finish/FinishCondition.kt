package omok.model.rule.finish

import omok.model.Board
import omok.model.FinishType
import omok.model.Player
import omok.model.Position

interface FinishCondition {
    fun finishType(
        board: Board,
        position: Position,
        player: Player,
    ): FinishType
}
