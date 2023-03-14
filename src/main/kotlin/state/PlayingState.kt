package state

import Stone
import Stones

class PlayingState(stones: Stones) : PlayerState(stones) {
    override fun add(newStone: Stone): PlayerState {
        val newStones = stones.add(newStone)
        if (newStones.checkWin(newStone)) return WinState(newStones)
        return PlayingState(newStones)
    }
}
