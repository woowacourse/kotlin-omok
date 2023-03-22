package domain.state

import domain.rule.OmokRule
import domain.stone.Stone
import domain.stone.Stones

abstract class FinishedState(stones: Stones) : PlayerState(stones) {
    override val isPlaying: Boolean = false

    override fun add(newStone: Stone, rule: OmokRule): PlayerState = this
}
