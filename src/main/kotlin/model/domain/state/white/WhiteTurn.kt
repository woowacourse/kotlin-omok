package model.domain.state.white

import model.domain.state.Omok
import model.domain.state.RetryTurn
import model.domain.state.Turn
import model.domain.tools.Board
import model.domain.tools.Stone

class WhiteTurn : Turn() {
    override val stoneColor: Stone
        get() = Stone.WHITE
    override val retryTurn: RetryTurn
        get() = RetryWhiteTurn()
    override val omok: Omok
        get() = WhiteOmok()
    override val turn: Turn
        get() = WhiteTurn()

    override fun isForbidden(board: Board, x: Int, y: Int) = false
}
