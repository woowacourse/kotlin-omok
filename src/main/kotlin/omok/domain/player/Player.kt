package omok.domain.player

import rule.OmokRule
import rule.type.Violation
import rule.wrapper.point.Point

abstract class Player {
    protected abstract val stonesBacking: MutableList<Point>
    val stones: List<Point> get() = stonesBacking.toList()

    protected abstract val rule: OmokRule

    fun addStone(point: Point) {
        stonesBacking.add(point)
    }

    fun checkWin(startPoint: Point): Boolean {
        return rule.checkSerialSameStonesBiDirection(stonesBacking, startPoint, WIN_STANDARD)
    }

    abstract fun isViolation(
        otherStones: List<Point>,
        startPoint: Point,
    )

    companion object {
        private const val WIN_STANDARD = 5

        private const val ERROR_DOUBLE_THREE = "3x3 위치에 놓을 수 없습니다"
        private const val ERROR_DOUBLE_FOUR = "4x4 위치에 놓을 수 없습니다"
        private const val ERROR_OVER_LINE = "장목 위치에 놓을 수 없습니다"

        fun dealViolation(violation: Violation) {
            when (violation) {
                Violation.DOUBLE_THREE -> throw IllegalStateException(ERROR_DOUBLE_THREE)
                Violation.DOUBLE_FOUR -> throw IllegalStateException(ERROR_DOUBLE_FOUR)
                Violation.OVERLINE -> throw IllegalStateException(ERROR_OVER_LINE)
                Violation.NONE -> {}
            }
        }
    }
}
