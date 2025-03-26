package omok.domain.state

import omok.domain.OmokBoard
import omok.domain.Point
import omok.domain.stone.StoneColor

abstract class Playing(
    override val omokBoard: OmokBoard,
) : State {
    abstract val stoneColor: StoneColor

    abstract fun place(point: Point): State
}
