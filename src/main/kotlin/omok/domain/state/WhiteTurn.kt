package omok.domain.state

import omok.domain.OmokBoard
import omok.domain.Point
import omok.domain.rule.Violation
import omok.domain.stone.StoneColor

class WhiteTurn(
    override val omokBoard: OmokBoard,
) : Playing {
    override fun place(point: Point): State {
        val violation = omokBoard.checkViolation(StoneColor.WHITE, point)
        return when (violation) {
            Violation.OUT_OF_BOARD,
            Violation.OCCUPIED,
            Violation.DOUBLE_THREE,
            Violation.DOUBLE_FOUR,
            Violation.OVERLINE,
            -> throw IllegalArgumentException(ERROR_CANNOT_PLACE)

            Violation.NONE -> getStateResult(point)
        }
    }

    private fun getStateResult(point: Point): State {
        val newStones = omokBoard.put(StoneColor.WHITE, point)
        return when {
            newStones.isOmok(StoneColor.WHITE, point) -> Finished(newStones, StoneColor.WHITE)
            newStones.isFull() -> Finished(newStones, null)
            else -> BlackTurn(newStones)
        }
    }

    override fun nextStoneColor(): StoneColor = StoneColor.WHITE

    companion object {
        private const val ERROR_CANNOT_PLACE = "[ERROR] 돌을 놓을 수 없습니다."
    }
}
