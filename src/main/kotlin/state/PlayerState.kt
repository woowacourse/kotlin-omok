package state

import Stone
import Stones

abstract class PlayerState(protected val stones: Stones) {
    abstract fun add(newStone: Stone): PlayerState

    fun hasStone(stone: Stone): Boolean = stones.hasStone(stone)
}
