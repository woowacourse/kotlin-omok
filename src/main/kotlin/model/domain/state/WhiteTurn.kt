package model.domain.state

import model.domain.Stone

class WhiteTurn : Turn() {
    override val stoneColor: Stone
        get() = Stone.WHITE
    override val retryTurn: RetryTurn
        get() = RetryWhiteTurn()
    override val omok: Omok
        get() = WhiteOmok()
    override val turn: Turn
        get() = WhiteTurn()
}
