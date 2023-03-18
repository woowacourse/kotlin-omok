package domain.state

import domain.stone.Stone
import domain.stone.StoneType

abstract class End : State {

    override fun isValidPut(stone: Stone): Boolean {
        throw IllegalStateException(CANT_IS_VALID_PUT_ERROR)
    }

    abstract override fun getWinner(): StoneType

    override fun put(stone: Stone): State {
        throw IllegalStateException(CANT_PUT_ERROR)
    }

    companion object {
        private const val CANT_IS_VALID_PUT_ERROR = "게임이 끝나서 바둑알을 놓을 수 있는 위치인지 확인할 수 없습니다."
        private const val CANT_PUT_ERROR = "게임이 끝나서 바둑알을 놓을 수 없습니다."
    }
}
