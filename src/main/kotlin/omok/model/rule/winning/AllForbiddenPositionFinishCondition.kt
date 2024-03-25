package omok.model.rule.winning

import omok.model.Board
import omok.model.FinishType
import omok.model.Player
import omok.model.Position

class AllForbiddenPositionFinishCondition : FinishCondition {
    override fun finishType(
        board: Board,
        position: Position,
        player: Player,
    ): FinishType {
        if (board.emptyPosition { !player.canPlace(board, it) }) return FinishType.DRAW
        return FinishType.NOT_FINISH
    }
}
