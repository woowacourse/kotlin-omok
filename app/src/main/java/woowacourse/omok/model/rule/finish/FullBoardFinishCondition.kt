package woowacourse.omok.model.rule.finish

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.game.FinishType
import woowacourse.omok.model.player.Player

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
