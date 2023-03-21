package domain.rule

import domain.Stone
import domain.rule.data.Inclination

class LongMokRule : Rule {
    override val errorMessage: String = "흑돌은 장목이면 안됩니다."
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