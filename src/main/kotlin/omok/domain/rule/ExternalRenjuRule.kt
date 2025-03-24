package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.Position
import omok.domain.placeresult.Failure
import omok.domain.placeresult.PlaceResult
import omok.domain.placeresult.Success
import omok.domain.player.PlayerStone
import rule.BlackRenjuRule
import rule.type.Violation.DOUBLE_FOUR
import rule.type.Violation.DOUBLE_THREE
import rule.type.Violation.NONE
import rule.type.Violation.OVERLINE
import rule.wrapper.point.Point

class ExternalRenjuRule(
    private val renjuRule: BlackRenjuRule,
) : OmokRule {
    override fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult {
        val startPoint = playerStone.position.toExternalPoint()

        val blackPoints = omokBoard.blackStonePoints.map { it.toExternalPoint() }

        val whitePoints = omokBoard.whiteStonePoints.map { it.toExternalPoint() }

        val violateType = renjuRule.checkAnyFoulCondition(blackPoints, whitePoints, startPoint)

        return when (violateType) {
            DOUBLE_THREE, DOUBLE_FOUR, OVERLINE -> Failure.ExternalRenjuRule(violateType)
            NONE -> Success.Progress(playerStone)
        }
    }

    private fun Position.toExternalPoint(): Point = Point(row = this.row.value - 1, col = this.column.value - 1)
}
