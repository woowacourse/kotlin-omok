package model.domain.state.white

import model.domain.state.Omok
import model.domain.state.State
import model.domain.state.Turn
import model.domain.state.black.BlackTurn
import model.domain.tools.Board
import model.domain.tools.Stone

class WhiteTurn : Turn() {
    override val stoneColor: Stone
        get() = Stone.WHITE
    override val omok: Omok
        get() = WhiteOmok(stoneColor)
    override val turn: Turn
        get() = WhiteTurn()
    override val nextTurn: State
        get() = BlackTurn()

    override fun isForbidden(board: Board, x: Int, y: Int) = false
}
