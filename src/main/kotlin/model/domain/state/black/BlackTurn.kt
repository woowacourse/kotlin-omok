package model.domain.state.black

import model.domain.rule.OmokForbiddenRule
import model.domain.state.Omok
import model.domain.state.RetryTurn
import model.domain.state.Turn
import model.domain.tools.Board
import model.domain.tools.Stone

class BlackTurn : Turn() {
    override val stoneColor: Stone
        get() = Stone.BLACK
    override val retryTurn: RetryTurn
        get() = RetryBlackTurn()
    override val omok: Omok
        get() = BlackOmok()
    override val turn: Turn
        get() = BlackTurn()

    override fun isForbidden(board: Board, x: Int, y: Int) = (
        OmokForbiddenRule(board, stoneColor).countOpenFours(x, y) >= 2 ||
            OmokForbiddenRule(board, stoneColor).countOpenThrees(x, y) >= 2
        )
}
