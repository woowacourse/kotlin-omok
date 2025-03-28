package woowacourse.omok.domain.model.rule.place

import rule.BlackRenjuRule
import rule.WhiteRenjuRule
import rule.type.Violation.DOUBLE_FOUR
import rule.type.Violation.DOUBLE_THREE
import rule.type.Violation.NONE
import rule.type.Violation.OVERLINE
import rule.wrapper.point.Point
import woowacourse.omok.domain.model.omokboard.IntersectionState
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.player.StoneColor

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
            omokBoard.snapshot
                .filter { it.value == IntersectionState.OCCUPIED_BLACK }
                .keys
                .map { it.toExternalPoint() }

        val whitePoints =
            omokBoard.snapshot
                .filter { it.value == IntersectionState.OCCUPIED_WHITE }
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
