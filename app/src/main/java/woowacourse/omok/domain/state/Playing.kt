package woowacourse.omok.domain.state

import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.Point
import woowacourse.omok.domain.stone.StoneColor

abstract class Playing(
    override val omokBoard: OmokBoard,
) : State {
    abstract val stoneColor: StoneColor

    abstract fun place(point: Point): State
}
