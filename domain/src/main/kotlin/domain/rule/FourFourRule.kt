package domain.rule

import domain.stone.Stone
import domain.stone.Stones
import domain.rule.data.Inclination

class FourFourRule : Rule {
    override val errorMessage: String = "흑돌은 44면 안됩니다."

    override fun checkRule(stones: Stones, justPlacedStone: Stone): Boolean {
        val nextStones = stones.addStone(justPlacedStone)
        var count44 = 0
        Inclination.values().forEach {
            if (isOpen4WithThisInclination(nextStones, justPlacedStone, it)) {
                count44++
            } else {
                it.directions.forEach { direction ->
                    if (is5WhenPutStoneWithDirection(
                            nextStones,
                            justPlacedStone,
                            direction
                        )
                    ) count44++
                }
            }
        }
        return count44 >= 2
    }
}