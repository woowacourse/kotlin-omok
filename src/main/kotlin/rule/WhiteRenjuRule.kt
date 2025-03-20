package rule

import Stone
import rule.type.Foul
import rule.type.Violation
import rule.type.Violation.Companion.OVERLINE_SIZE
import rule.wrapper.position.Position

class WhiteRenjuRule(
    boardWidth: Int = DEFAULT_BOARD_WIDTH,
    boardHeight: Int = DEFAULT_BOARD_HEIGHT,
) : OmokRule(boardWidth, boardHeight) {
    override fun checkDoubleFoul(
        blackPositions: List<Stone>,
        whitePositions: List<Stone>,
        startPosition: Position,
        foul: Foul,
    ): Violation = Violation.NONE

    override fun checkOverline(
        stonesPositions: List<Stone>,
        startPosition: Position,
    ): Violation {
        if (checkSerialSameStonesBiDirection(stonesPositions, startPosition, OVERLINE_SIZE)) return Violation.OVERLINE
        return Violation.NONE
    }

    companion object {
        private const val DEFAULT_BOARD_WIDTH = 15
        private const val DEFAULT_BOARD_HEIGHT = 15
    }
}
