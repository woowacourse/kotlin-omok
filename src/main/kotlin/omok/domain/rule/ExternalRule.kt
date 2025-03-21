package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.PointState
import omok.domain.omokboard.Position
import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor
import rule.BlackRenjuRule
import rule.WhiteRenjuRule
import rule.type.Violation.DOUBLE_FOUR
import rule.type.Violation.DOUBLE_THREE
import rule.type.Violation.NONE
import rule.type.Violation.OVERLINE
import rule.wrapper.point.Point

class ExternalRule : OmokRule {
    private lateinit var renjuRule: rule.OmokRule
    private val blackRenjuRule = BlackRenjuRule()
    private val whiteRenjuRule = WhiteRenjuRule()

    override fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult {
        renjuRule =
            when (playerStone.color) {
                StoneColor.BLACK -> blackRenjuRule
                StoneColor.WHITE -> whiteRenjuRule
            }

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
            DOUBLE_THREE -> PlaceResult.Failure.DoubleThreeViolation
            DOUBLE_FOUR -> PlaceResult.Failure.DoubleFourViolation
            OVERLINE -> PlaceResult.Failure.OverlineViolation
            NONE -> PlaceResult.Success.Progress(playerStone)
        }
    }

    private fun Position.toExternalPoint(): Point = Point(row = this.row.value - 1, col = this.column.value - 1)
}
