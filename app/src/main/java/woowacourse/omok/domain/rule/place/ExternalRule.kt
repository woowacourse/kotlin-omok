package woowacourse.omok.domain.rule.place

import rule.BlackRenjuRule
import rule.WhiteRenjuRule
import rule.type.Violation.DOUBLE_FOUR
import rule.type.Violation.DOUBLE_THREE
import rule.type.Violation.NONE
import rule.type.Violation.OVERLINE
import rule.wrapper.point.Point
import woowacourse.omok.domain.omokboard.IntersectionState
import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.omokboard.Position
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor

class ExternalRule : PlaceRule {
    private lateinit var renjuRule: rule.OmokRule
    private val blackRenjuRule = BlackRenjuRule()
    private val whiteRenjuRule = WhiteRenjuRule()

    override fun perform(
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
                .filter { it.value.state == IntersectionState.OCCUPIED_BLACK }
                .keys
                .map { it.toExternalPoint() }

        val whitePoints =
            omokBoard.value
                .filter { it.value.state == IntersectionState.OCCUPIED_WHITE }
                .keys
                .map { it.toExternalPoint() }

        val violateType = renjuRule.checkAnyFoulCondition(blackPoints, whitePoints, startPoint)

        return when (violateType) {
            DOUBLE_THREE -> PlaceResult.Failure.DoubleThreeViolation
            DOUBLE_FOUR -> PlaceResult.Failure.DoubleFourViolation
            OVERLINE -> PlaceResult.Failure.OverlineViolation
            NONE -> PlaceResult.Success
        }
    }

    private fun Position.toExternalPoint(): Point = Point(row = this.row - 1, col = this.column - 1)
}
