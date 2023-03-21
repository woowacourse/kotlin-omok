package domain.state

import domain.stone.Stone
import domain.stone.StoneType

abstract class Running : State {
    abstract override fun put(stone: Stone): State

    override fun getWinner(): StoneType {
        throw IllegalStateException(CANT_GET_WINNER_ERROR)
    }

    companion object {
        private const val CANT_GET_WINNER_ERROR = "게임이 진행중이라 우승한 돌을 알 수 없습니다."
    }
}
