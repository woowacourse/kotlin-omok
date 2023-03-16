package model.domain.state

import model.domain.Stone

class BlackTurn : Turn() {
    override val stoneColor: Stone
        get() = Stone.BLACK
    override val retryTurn: RetryTurn
        get() = RetryBlackTurn()
    override val omok: Omok
        get() = BlackOmok()
    override val turn: Turn
        get() = BlackTurn()
}
