package domain.rule

import domain.point.Point
import domain.rule.type.Foul
import domain.rule.type.Violation

class WhiteRenjuRule(
    boardWidth: Int = DEFAULT_BOARD_WIDTH,
    boardHeight: Int = DEFAULT_BOARD_HEIGHT,
) : OmokRule(boardWidth, boardHeight) {
    override fun checkDoubleFoul(
        myPoints: List<Point>,
        otherPoints: List<Point>,
        startPoint: Point,
        foul: Foul,
    ): Violation = Violation.NONE

    override fun checkOverline(
        stonesPoints: List<Point>,
        startPoint: Point,
    ): Violation = Violation.NONE

    companion object {
        private const val DEFAULT_BOARD_WIDTH = 15
        private const val DEFAULT_BOARD_HEIGHT = 15
    }
}
