package woowacourse.omok.domain.state

import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.rule.Violation
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

abstract class Playing(
    override val omokBoard: OmokBoard,
) : State {
    abstract val stoneColor: StoneColor

    fun place(newStone: Stone): PlaceResult {
        val violation = omokBoard.checkViolation(newStone)
        return when (violation) {
            Violation.OUT_OF_BOARD -> PlaceResult.ForbiddenMove.OutOfBoard()
            Violation.OCCUPIED -> PlaceResult.ForbiddenMove.Occupied()
            Violation.DOUBLE_THREE -> PlaceResult.ForbiddenMove.DoubleThree()
            Violation.DOUBLE_FOUR -> PlaceResult.ForbiddenMove.DoubleFour()
            Violation.OVERLINE -> PlaceResult.ForbiddenMove.Overline()
            Violation.NONE -> {
                val newBoard = omokBoard.place(newStone)
                PlaceResult.Placed(nextState(newBoard, newStone))
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
            else -> nextTurn(omokBoard)
        }

    abstract fun nextTurn(omokBoard: OmokBoard): Playing
}
