package domain.rule

import domain.Stone
import domain.rule.data.Inclination

class LongMokBlackRule : BlackRule() {

    override fun checkRule(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean {

        if (Inclination.values().any {
                calculateContinuousBlackStonesCountFromRecentStoneWithInclination(
                    blackStones + nextStone,
                    nextStone,
                    it
                ) > 6
            }) return false
        return true
    }
}