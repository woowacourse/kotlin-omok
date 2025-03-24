package omok.domain.state

import omok.domain.OmokBoard
import omok.domain.Point
import omok.domain.rule.Violation
import omok.domain.stone.StoneColor

class BlackTurn(
    override val omokBoard: OmokBoard,
) : Playing {
    override fun place(point: Point): State {
        val violation = omokBoard.checkViolation(StoneColor.BLACK, point)
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
        val newStones = omokBoard.put(StoneColor.BLACK, point)
        return when {
            newStones.isOmok(StoneColor.BLACK, point) -> Finished(newStones, StoneColor.BLACK)
            newStones.isFull() -> Finished(newStones, null)
            else -> WhiteTurn(newStones)
        }
    }

    override fun nextStoneColor(): StoneColor = StoneColor.BLACK

    companion object {
        private const val ERROR_CANNOT_PLACE = "[ERROR] 돌을 놓을 수 없습니다."
    }
}
