package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.PointState
import omok.domain.omokboard.Position
import omok.domain.player.PlayerStone
import omok.domain.rule.PlaceResult.Failure
import omok.domain.rule.PlaceResult.Success
import rule.BlackRenjuRule
import rule.type.Violation.DOUBLE_FOUR
import rule.type.Violation.DOUBLE_THREE
import rule.type.Violation.NONE
import rule.type.Violation.OVERLINE
import rule.wrapper.point.Point

class ExternalRule(
    private val renjuRule: BlackRenjuRule,
) : OmokRule {
    override fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult {
        val startPoint = playerStone.position.toExternalPoint()

        val blackPoints =
            omokBoard.value
                .filter { it.value.state == PointState.OCCUPIED_BLACK }
                .keys
                .map { it.toExternalPoint() }

        val whitePoints =
            omokBoard.value
                .filter { it.value.state == PointState.OCCUPIED_WHITE }
                .keys
                .map { it.toExternalPoint() }

        val violateType = renjuRule.checkAnyFoulCondition(blackPoints, whitePoints, startPoint)

        return when (violateType) {
            DOUBLE_THREE -> Failure.DoubleThreeViolation
            DOUBLE_FOUR -> Failure.DoubleFourViolation
            OVERLINE -> Failure.OverlineViolation
            NONE -> Success.Progress(playerStone)
        }
    }

    private fun Position.toExternalPoint(): Point = Point(row = this.row.value - 1, col = this.column.value - 1)
}
