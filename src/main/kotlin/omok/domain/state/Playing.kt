package omok.domain.state

import omok.domain.OmokBoard
import omok.domain.Point
import omok.domain.rule.Violation
import omok.domain.stone.StoneColor

abstract class Playing(
    override val omokBoard: OmokBoard,
) : State {
    abstract val stoneColor: StoneColor

    abstract fun place(point: Point): State

    fun checkViolation(point: Point) {
        val violation = omokBoard.checkViolation(stoneColor, point)
        when (violation) {
            Violation.OUT_OF_BOARD -> throw IllegalArgumentException(ERROR_OUT_OF_BOARD)
            Violation.OCCUPIED -> throw IllegalArgumentException(ERROR_ALREADY_OCCUPIED)
            Violation.DOUBLE_THREE, Violation.DOUBLE_FOUR, Violation.OVERLINE,
            -> throw IllegalArgumentException(ERROR_RENJU_RULE)

            Violation.NONE -> return
        }
    }

    companion object {
        private const val ERROR_OUT_OF_BOARD = "오목판의 범위를 넘어간 좌표입니다."
        private const val ERROR_ALREADY_OCCUPIED = "이미 돌이 놓여져 있습니다."
        private const val ERROR_RENJU_RULE = "돌을 놓을 수 없습니다. 금수입니다."
    }
}
