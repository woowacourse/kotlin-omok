package woowacourse.omok.model.state

import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone
import woowacourse.omok.model.Position
import woowacourse.omok.model.StoneColor
import woowacourse.omok.model.rule.StonePlaceRule

class BlackTurn(stonePlaceRule: StonePlaceRule, board: Board) :
    Running(stonePlaceRule, board) {
    override val isFinished: Boolean = false

    override fun put(position: Position): GameState {
        val newStone = OmokStone(position, StoneColor.BLACK)
        if (canPut(newStone)) {
            val newBoard = board + newStone
            if (newBoard.isInOmok(position)) return Finish(newBoard)
            return WhiteTurn(StonePlaceRule(), newBoard)
        }
        return this
    }
}
