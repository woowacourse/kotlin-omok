package omock.model.state

import omock.model.turn.Turn

abstract class Placed(private val stone: Stone) : StoneState {
    override fun put(turn: Turn): StoneState {
        throw IllegalStateException(ERROR_STONE_DUPLICATION)
    }

    override fun rollback(): StoneState {
        return Clear(stone)
    }

    companion object {
        private const val ERROR_STONE_DUPLICATION = "이미 돌이 있습니다."
        const val BLACK_NUMBER = 1
        const val WHITE_NUMBER = 2
    }
}
