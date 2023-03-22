package domain.state

import domain.stone.Stone

interface State {
    fun put(stone: Stone): State
}
