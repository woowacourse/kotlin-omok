package domain.state

import domain.rule.OmokRule
import domain.stone.Stone
import domain.stone.StoneType
import domain.stone.Stones

class BlackTurn(stones: Stones, omokRule: OmokRule) : Running(stones, omokRule) {
    override fun put(stone: Stone): State {
        if (!isValidPut(stone)) return BlackTurn(stones, omokRule)
        stones.add(stone)
        if (isOmokCondition(stone)) return End(StoneType.BLACK)
        return WhiteTurn(stones, omokRule)
    }
}
