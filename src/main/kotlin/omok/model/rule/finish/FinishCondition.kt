package omok.model.rule.finish

import omok.model.board.Board
import omok.model.board.Position
import omok.model.game.FinishType
import omok.model.player.Player

interface FinishCondition {
    fun finishType(
        board: Board,
        position: Position,
        player: Player,
    ): FinishType
}
