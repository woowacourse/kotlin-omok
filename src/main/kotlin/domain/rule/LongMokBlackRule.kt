package domain.rule

import domain.Stone
import domain.rule.data.Inclination

class LongMokBlackRule : Rule() {

    override fun checkRule(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean {

        return Inclination.values().any {
            calculateContinuousBlackStonesCountFromRecentStoneWithInclination(
                blackStones + nextStone,
                whiteStones,
                nextStone,
                it
            ) > 6
        }
    }
}