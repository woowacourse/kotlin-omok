package woowacourse.omok.domain.state

import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.Point
import woowacourse.omok.domain.rule.Violation
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class BlackTurn(
    override val omokBoard: OmokBoard,
) : Playing(omokBoard) {
    override val stoneColor: StoneColor = StoneColor.BLACK

    override fun place(point: Point): PlaceResult {
        val newStone = Stone(stoneColor, point)
        val violation = omokBoard.checkViolation(newStone)
        return when (violation) {
            Violation.OUT_OF_BOARD -> PlaceResult.ForbiddenMove.OutOfBoard()
            Violation.OCCUPIED -> PlaceResult.ForbiddenMove.Occupied()
            Violation.DOUBLE_THREE -> PlaceResult.ForbiddenMove.DoubleThree()
            Violation.DOUBLE_FOUR -> PlaceResult.ForbiddenMove.DoubleFour()
            Violation.OVERLINE -> PlaceResult.ForbiddenMove.Overline()
            Violation.NONE -> {
                val newBoard = omokBoard.place(newStone)
                return PlaceResult.Placed(nextState(newBoard, newStone))
            }
        }
    }

    private fun nextState(
        omokBoard: OmokBoard,
        stone: Stone,
    ): State =
        when {
            omokBoard.isOmok(stone) -> Finished(omokBoard, stoneColor)
            omokBoard.isFull() -> Finished(omokBoard, null)
            else -> WhiteTurn(omokBoard)
        }
}
