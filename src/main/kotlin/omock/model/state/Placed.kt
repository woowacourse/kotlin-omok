package omock.model.state

import omock.model.turn.Turn

abstract class Placed(private val stone: Stone) : StoneState {
    override fun put(turn: Turn): StoneState {
        throw IllegalArgumentException(ERROR_STONE_DUPLICATION)
    }

    override fun rollback(): StoneState {
        return Clear(stone)
    }

    companion object {
        private const val ERROR_STONE_DUPLICATION = "이미 돌이 있습니다."
    }
}
