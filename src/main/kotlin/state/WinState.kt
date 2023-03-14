package state

import Stone
import Stones

class WinState(stones: Stones) : PlayerState(stones) {
    override fun add(newStone: Stone): PlayerState = this
}
