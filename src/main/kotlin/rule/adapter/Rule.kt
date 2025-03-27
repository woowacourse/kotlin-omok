package rule.adapter

import domain.stone.Stone
import domain.stone.Stones
import rule.lib.type.Violation

interface Rule {
    fun isWin(
        playerStones: Stones,
        otherStones: Stones,
        placedStone: Stone,
    ): Boolean

    fun violation(
        playerStones: Stones,
        otherStones: Stones,
        placedStone: Stone,
    ): Violation
}
