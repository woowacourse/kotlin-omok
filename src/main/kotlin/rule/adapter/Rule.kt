package rule.adapter

import domain.stone.Stone
import domain.stone.StoneColor
import domain.stone.Stones
import rule.type.Violation

interface Rule {
    fun isWinByStoneColor(
        stoneColor: StoneColor,
        playerStones: Stones,
        otherStones: Stones,
        placedStone: Stone,
    ): Boolean

    fun violation(
        stoneColor: StoneColor,
        playerStones: Stones,
        otherStones: Stones,
        placedStone: Stone,
    ): Violation
}
