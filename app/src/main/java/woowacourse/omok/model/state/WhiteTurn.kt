package woowacourse.omok.model.state

import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone
import woowacourse.omok.model.Position
import woowacourse.omok.model.StoneColor
import woowacourse.omok.model.rule.RenjuRule
import woowacourse.omok.model.rule.StonePlaceRule

class WhiteTurn(stonePlaceRule: StonePlaceRule, board: Board) : Running(stonePlaceRule, board) {
    override fun put(position: Position): GameState {
        val newStone = OmokStone(position, StoneColor.WHITE)
        if (canPut(newStone)) {
            val newBoard = board + newStone
            if (newBoard.isInOmok(position)) return Finish(newBoard)
            return BlackTurn(RenjuRule, newBoard)
        }
        return this
    }
}
