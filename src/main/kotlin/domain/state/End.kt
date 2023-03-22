package domain.state

import domain.stone.Stone
import domain.stone.StoneType

class End(private val stone: Stone) : State {

    override fun put(stone: Stone): State {
        throw IllegalStateException(CANT_PUT_ERROR)
    }

    fun getWinner(): StoneType = stone.type

    companion object {
        private const val CANT_PUT_ERROR = "게임이 끝나서 바둑알을 놓을 수 없습니다."
    }
}
