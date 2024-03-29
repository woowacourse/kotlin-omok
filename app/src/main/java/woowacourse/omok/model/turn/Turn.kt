package woowacourse.omok.model.turn

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.entity.Point
import woowacourse.omok.model.entity.StoneColor

interface Turn {
    val board: Board

    fun placeStone(
        point: Point,
        onInappropriate: (String) -> Unit,
    ): Turn

    fun color(): StoneColor
}
