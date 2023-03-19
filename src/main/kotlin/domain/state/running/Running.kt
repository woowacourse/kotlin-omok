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

    override fun isValidPut(stone: Stone): Boolean {
        return !board.stones.containsPosition(stone)
    }
}
