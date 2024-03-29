package woowacourse.omok.model.turn

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.entity.Point
import woowacourse.omok.model.entity.StoneColor

class Finished(override val board: Board = Board(), private val color: StoneColor) : Turn {
    override fun placeStone(
        point: Point,
        onInappropriate: (String) -> Unit,
    ): Turn {
        throw IllegalStateException("완료된 상태에서는 돌을 놓을 수 없습니다.")
    }

    override fun color(): StoneColor = color
}
