package domain.state

import domain.rule.OmokRule
import domain.stone.Stone
import domain.stone.StoneColor
import domain.stone.Stones

class WinState(stones: Stones) : PlayerState(stones) {
    override fun add(newStone: Stone, otherStones: Stones, rule: OmokRule, turn: StoneColor): PlayerState = this
}
