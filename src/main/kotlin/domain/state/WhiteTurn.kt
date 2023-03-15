package domain.state

import domain.rule.OmokRule
import domain.stone.Stone
import domain.stone.Stones

class WhiteTurn(stones: Stones, omokRule: OmokRule) : Running(stones, omokRule) {
    override fun put(stone: Stone): State {
        TODO("Not yet implemented")
    }
}
