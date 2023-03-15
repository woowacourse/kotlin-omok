package domain.state

import domain.rule.OmokRule
import domain.stone.Stone
import domain.stone.StoneType
import domain.stone.Stones

abstract class Running(val stones: Stones, val omokRule: OmokRule) : State {
    abstract override fun put(stone: Stone): State

    override fun getWinner(): StoneType {
        TODO("Not yet implemented")
    }

    override fun isValidPut(stone: Stone): Boolean {
        return !stones.containsPosition(stone)
    }

    override fun isOmokCondition(stone: Stone): Boolean {
        return omokRule.isOmokCondition(stones.matrixBoard(), stone)
    }
}
