package omok.domain.rule

import omok.domain.OmokGame
import omok.domain.Point

class WhiteStoneRule(
    boardSize: Int = OmokGame.DEFAULT_BOARD_SIZE,
) : OmokRule(boardSize) {
    override fun checkViolation(
        thisPoints: Set<Point>,
        otherPoints: Set<Point>,
        startPoint: Point,
    ): Violation = Violation.NONE
}
