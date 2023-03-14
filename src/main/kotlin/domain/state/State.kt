package domain.state

import domain.Stone

interface State {
    fun put(stone: Stone): State
}
