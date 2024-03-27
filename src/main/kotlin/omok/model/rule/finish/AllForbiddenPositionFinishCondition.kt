package omok.model.rule.finish

import omok.model.board.Board
import omok.model.board.Position
import omok.model.game.FinishType
import omok.model.player.Player

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
