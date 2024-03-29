package omok.model.rule.finish

import omok.model.board.Board
import omok.model.board.Position
import omok.model.game.FinishType
import omok.model.player.Player

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
