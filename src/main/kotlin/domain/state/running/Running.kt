package domain.state.running

import domain.state.State
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StoneType

abstract class Running(val board: Board) : State {

    abstract override fun put(stone: Stone): State

    override fun getWinner(): StoneType {
        TODO("Not yet implemented")
    }

    fun isValidPut(stone: Stone): Boolean =
        !board.stones.containsPosition(stone.position)
}
