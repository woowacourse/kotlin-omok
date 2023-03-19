package domain.state

import domain.stone.Stones

class WinState(stones: Stones) : FinishedState(stones) {
    override val isFoul: Boolean = false
}
