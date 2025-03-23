package rule

import domain.position.Position
import domain.stone.Stone
import rule.type.Foul
import rule.type.Violation

class WhiteRenjuRule : OmokRule() {
    override fun checkWin(
        blackStones: List<Stone>,
        whiteStones: List<Stone>,
        startPosition: Position,
    ): Boolean {
        val satisfyWin = checkSerialSameStonesBiDirection(whiteStones, startPosition, WIN_STANDARD)

        return satisfyWin
    }

    override fun checkDoubleFoul(
        blackStones: List<Stone>,
        whiteStones: List<Stone>,
        startPosition: Position,
        foul: Foul,
    ): Violation = Violation.NONE

    override fun checkOverline(
        stones: List<Stone>,
        startPosition: Position,
    ): Violation = Violation.NONE
}
