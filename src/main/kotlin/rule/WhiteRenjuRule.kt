package rule

import Stone
import rule.type.Foul
import rule.type.Violation
import rule.wrapper.position.Position

class WhiteRenjuRule(
    boardWidth: Int = DEFAULT_BOARD_WIDTH,
    boardHeight: Int = DEFAULT_BOARD_HEIGHT,
) : OmokRule(boardWidth, boardHeight) {
    override fun checkWin(
        blackPositions: List<Stone>,
        whitePositions: List<Stone>,
        startPosition: Position,
    ): Boolean {
        val satisfyWin = checkSerialSameStonesBiDirection(whitePositions, startPosition, WIN_STANDARD)
        val koState = checkAnyFoulCondition(blackPositions, whitePositions, startPosition)

        return satisfyWin && koState != Violation.OVERLINE
    }

    override fun checkDoubleFoul(
        blackPositions: List<Stone>,
        whitePositions: List<Stone>,
        startPosition: Position,
        foul: Foul,
    ): Violation = Violation.NONE

    override fun checkOverline(
        stonesPositions: List<Stone>,
        startPosition: Position,
    ): Violation = Violation.NONE

    companion object {
        private const val DEFAULT_BOARD_WIDTH = 15
        private const val DEFAULT_BOARD_HEIGHT = 15
    }
}
