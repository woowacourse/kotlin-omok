package omok.model.adapter

import omok.model.game.FoulCondition
import omok.model.stone.Stone

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

    fun Stone.toPair(): Pair<Int, Int> = point.row to point.col

    fun Set<Stone>.toPairList(): List<Pair<Int, Int>> = map { it.toPair() }
}
