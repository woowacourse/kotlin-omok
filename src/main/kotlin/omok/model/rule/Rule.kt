package omok.model.rule

import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Position

interface Rule {
    fun isWin(
        stones: Map<Position, StoneColor>,
        lastStone: Stone,
    ): Boolean

    fun validate(
        stones: Map<Position, StoneColor>,
        nextPosition: Position,
        color: StoneColor,
    )
}
