package domain.state

import domain.stone.Stone

abstract class Running : State {
    abstract override fun put(stone: Stone): State
}
