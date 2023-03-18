package domain.state

import domain.stone.Board
import domain.stone.Stone
import domain.stone.StoneType

abstract class Running(val board: Board) : State {
    abstract override fun put(stone: Stone): State

    override fun getWinner(): StoneType {
        throw IllegalStateException(CANT_GET_WINNER_ERROR)
    }

    override fun isValidPut(stone: Stone): Boolean {
        return !board.stones.containsPosition(stone)
    }

    companion object {
        private const val CANT_GET_WINNER_ERROR = "게임이 진행중이라 우승한 돌을 알 수 없습니다."
    }
}
