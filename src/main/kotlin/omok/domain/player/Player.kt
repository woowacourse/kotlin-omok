package omok.domain.player

import omok.domain.Position
import omok.domain.lib.OmokRule

abstract class Player {
    protected abstract val stonesBacking: MutableList<Position>
    val stones: List<Position> get() = stonesBacking.toList()

    protected abstract val rule: OmokRule

    fun addStone(point: Position) {
        stonesBacking.add(point)
    }

//    fun checkWin(startPoint: Position): Boolean {
//        return rule.checkSerialSameStonesBiDirection(stonesBacking, startPoint, WIN_STANDARD)
//    }

    abstract fun isViolation(
        otherStones: List<Position>,
        startPoint: Position,
    )

//    companion object {
//        private const val WIN_STANDARD = 5
//
//        private const val ERROR_DOUBLE_THREE = "3x3 위치에 놓을 수 없습니다"
//        private const val ERROR_DOUBLE_FOUR = "4x4 위치에 놓을 수 없습니다"
//        private const val ERROR_OVER_LINE = "장목 위치에 놓을 수 없습니다"
//
//        fun dealViolation(violation: Violation) {
//            when (violation) {
//                Violation.DOUBLE_THREE -> throw IllegalStateException(ERROR_DOUBLE_THREE)
//                Violation.DOUBLE_FOUR -> throw IllegalStateException(ERROR_DOUBLE_FOUR)
//                Violation.OVERLINE -> throw IllegalStateException(ERROR_OVER_LINE)
//                Violation.NONE -> {}
//            }
//        }
//    }
}
