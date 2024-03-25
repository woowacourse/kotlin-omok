package omok.model.rule.finish

import omok.model.Board
import omok.model.FinishType
import omok.model.Player
import omok.model.Position

class FullBoardFinishCondition : FinishCondition {
    override fun finishType(
        board: Board,
        position: Position,
        player: Player,
    ): FinishType {
        if (board.isFull()) return FinishType.DRAW
        return FinishType.NOT_FINISH
    }
}
