package player

import BoardState
import Position
import Stone
import state.PlayerState

abstract class Player(protected val state: PlayerState) {
    abstract val boardState: BoardState

    fun isPlaced(stone: Stone): Boolean = state.hasStone(stone)
    fun getPlaced(): List<Position> = state.getPlaced()
    abstract fun putStone(stone: Stone): Player
}
