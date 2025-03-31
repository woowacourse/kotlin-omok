package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.stone.BlackStones
import woowacourse.omok.domain.model.stone.StoneColor
import woowacourse.omok.domain.model.stone.WhiteStones

class BlackTurn(
    override val blackStones: BlackStones,
    override val whiteStones: WhiteStones,
) : Playing() {
    override fun place(
        point: Point,
        boardSize: Int,
        onBoardUpdated: (BlackStones, WhiteStones) -> Unit,
    ): State {
        val newStones = blackStones + point
        return when {
            blackStones.isOmok(point) -> Finished.Win(StoneColor.BLACK)
            newStones.points.size + whiteStones.points.size >= boardSize * boardSize -> Finished.Draw
            blackStones.isDoubleThreeFoul(whiteStones, point) -> Foul.DoubleThree
            blackStones.isDoubleFourFoul(whiteStones, point) -> Foul.DoubleFour
            blackStones.isOverLine(point) -> Foul.OverLine
            blackStones.contains(point) || whiteStones.contains(point) -> Foul.Duplicated
            else -> WhiteTurn(newStones, whiteStones)
        }.also {
            onBoardUpdated(newStones, whiteStones)
        }
    }

    override fun nextStoneColor(): StoneColor = StoneColor.BLACK
}
