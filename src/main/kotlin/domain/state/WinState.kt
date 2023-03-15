package domain.state

import domain.stone.Stone
import domain.stone.Stones

class WinState(stones: Stones) : PlayerState(stones) {
    override fun add(newStone: Stone): PlayerState = this
}
