package state

import Position
import Stone
import Stones

abstract class PlayerState(protected val stones: Stones = Stones()) {
    abstract fun add(newStone: Stone): PlayerState

    fun hasStone(stone: Stone): Boolean = stones.hasStone(stone)

    fun getPlaced(): List<Position> = stones.getPositions()
}
