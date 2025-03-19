package omok.domain.state

import omok.domain.Point
import omok.domain.StoneColor
import omok.domain.Stones

abstract class Finished(
    override val blackStones: Stones,
    override val whiteStones: Stones,
) : State {
    abstract val winnerColor: StoneColor?

    override fun place(
        point: Point,
        boardSize: Int,
    ): State = throw IllegalStateException()

    override fun lastStonePoint(): Point = throw IllegalStateException()

    override fun nextStoneColor(): StoneColor = throw IllegalStateException()
}
