package omok.model.adapter

import omok.model.Stone

interface RuleAdapter {
    fun checkAnyFoulCondition(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
    ): FoulCondition

    fun checkWin(
        blackStones: Set<Stone>,
        whiteStones: Set<Stone>,
        startStone: Stone,
        sameStoneToCheck: Int,
    ): Boolean
}
