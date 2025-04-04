package model.judge

import woowacourse.omok.model.Direction
import woowacourse.omok.model.Position
import woowacourse.omok.model.Stone

interface Rule {
    fun checkFoulByAllDirections(
        stone: Stone,
        stones: List<Stone>,
    ): Boolean

    fun checkFoul(
        stone: Stone,
        stones: List<Stone>,
        startPosition: Position,
        lastPosition: Position,
        direction: Direction,
    ): List<Stone>?
}
