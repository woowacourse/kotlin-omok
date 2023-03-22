package domain.state

import domain.stone.Stones

class FoulState(stones: Stones) : FinishedState(stones) {
    override val isFoul: Boolean = true
}
