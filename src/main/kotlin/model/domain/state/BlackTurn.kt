package model.domain.state

import model.domain.Board
import model.domain.Stone
import model.domain.rule.OmokForbiddenRule

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
