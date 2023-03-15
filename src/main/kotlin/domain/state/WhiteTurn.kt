package domain.state

import domain.rule.OmokRule
import domain.stone.Stone
import domain.stone.StoneType
import domain.stone.Stones

class WhiteTurn(stones: Stones, omokRule: OmokRule) : Running(stones, omokRule) {
    override fun put(stone: Stone): State {
        if (!isValidPut(stone)) return WhiteTurn(stones, omokRule)
        stones.add(stone)
        if (isOmokCondition(stone)) return End(StoneType.WHITE)
        return BlackTurn(stones, omokRule)
    }
}
