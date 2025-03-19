package omok.model.domain.rule

import omok.model.domain.omokboard.OmokBoard
import omok.model.domain.omokboard.PointState
import omok.model.domain.omokboard.Position
import omok.model.domain.player.PlayerStone
import rule.BlackRenjuRule
import rule.type.Violation
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
            Violation.DOUBLE_THREE -> PlaceResult.Failure.DoubleThreeViolation
            Violation.DOUBLE_FOUR -> PlaceResult.Failure.DoubleFourViolation
            Violation.OVERLINE -> PlaceResult.Failure.OverlineViolation
            Violation.NONE -> PlaceResult.Success.Progress(playerStone)
        }
    }

    private fun Position.toExternalPoint(): Point = Point(row = this.row.value - 1, col = this.column.value - 1)
}
