package domain.rule

import domain.Stone
import domain.rule.data.Inclination

class FourFourBlackRule : BlackRule() {

    override fun checkRule(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean {
        val nextBlackStones = blackStones + nextStone
        var count44 = 0
        Inclination.values().forEach {
            it.directions.forEach { direction ->
                if (is5WhenPutStoneWithDirection(nextBlackStones, whiteStones, nextStone, direction)) count44++
            }
        }
        return count44 < 2
    }
}