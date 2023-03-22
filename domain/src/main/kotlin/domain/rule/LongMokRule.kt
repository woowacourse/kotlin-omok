package domain.rule

import domain.stone.Stone
import domain.stone.Stones
import domain.rule.data.Inclination

class LongMokRule : Rule {
    override val errorMessage: String = "흑돌은 장목이면 안됩니다."
    override fun checkRule(stones: Stones, justPlacedStone: Stone): Boolean {
        return Inclination.values().any {
            calculateContinuousStonesCountWithInclination(
                stones.addStone(justPlacedStone),
                justPlacedStone,
                it
            ) >= 6
        }
    }
}