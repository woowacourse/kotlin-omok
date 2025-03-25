package omok.domain.rule

import omok.domain.OmokBoard
import omok.domain.Point

class WhiteStoneRule(
    boardSize: Int = OmokBoard.DEFAULT_BOARD_SIZE,
) : OmokRule(boardSize) {
    override fun checkViolation(
        thisPoints: Set<Point>,
        otherPoints: Set<Point>,
        startPoint: Point,
    ): Violation = Violation.NONE
}
