package domain.rule

import domain.Stone
import domain.rule.data.Inclination

class FourFourRule : Rule {
    override val errorMessage: String = "흑돌은 44면 안됩니다."

    override fun checkRule(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean {
        val nextBlackStones = blackStones + nextStone
        var count44 = 0
        Inclination.values().forEach {
            if (isOpen4WithThisInclination(nextBlackStones, whiteStones, nextStone, it)) {
                count44++
            } else {
                it.directions.forEach { direction ->
                    if (is5WhenPutStoneWithDirection(nextBlackStones, whiteStones, nextStone, direction)) count44++
                }
            }
        }
        return count44 >= 2
    }
}