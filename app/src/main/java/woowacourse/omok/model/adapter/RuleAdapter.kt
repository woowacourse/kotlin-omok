package woowacourse.omok.model.adapter

import woowacourse.omok.model.game.FoulCondition
import woowacourse.omok.model.stone.Stone

interface RuleAdapter {
    fun checkAnyFoulCondition(
        stones: Set<Stone>,
        startStone: Stone,
    ): FoulCondition

    fun checkWin(
        stones: Set<Stone>,
        startStone: Stone,
    ): Boolean

    fun Stone.toPair(): Pair<Int, Int> = point.row to point.col

    fun List<Stone>.toPairList(): List<Pair<Int, Int>> = map { it.toPair() }
}
