package woowacourse.omok.domain.rule

import rule.BlackRenjuRule
import rule.type.Violation.DOUBLE_FOUR
import rule.type.Violation.DOUBLE_THREE
import rule.type.Violation.NONE
import rule.type.Violation.OVERLINE
import rule.wrapper.point.Point
import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.omokboard.OmokBoardGridCell
import woowacourse.omok.domain.omokboard.Position
import woowacourse.omok.domain.placeresult.GameOnGoing
import woowacourse.omok.domain.placeresult.InvalidMove
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor

class ExternalRenjuRule(
    private val renjuRule: BlackRenjuRule,
) : OmokGameFinishRule {
    override fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult {
        val startPoint = playerStone.position.toExternalPoint()

        val blackPoints = extractPoints(omokBoard, StoneColor.BLACK)
        val whitePoints = extractPoints(omokBoard, StoneColor.WHITE)

        return when (val violateType = renjuRule.checkAnyFoulCondition(blackPoints, whitePoints, startPoint)) {
            DOUBLE_THREE, DOUBLE_FOUR, OVERLINE -> InvalidMove.ExternalRenjuRule(violateType)
            NONE -> GameOnGoing
        }
    }

    private fun extractPoints(
        omokBoard: OmokBoard,
        stoneColor: StoneColor,
    ): List<Point> {
        return omokBoard.value
            .filter { it.value is OmokBoardGridCell.OCCUPIED && (it.value as OmokBoardGridCell.OCCUPIED).color == stoneColor }
            .keys.map { it.toExternalPoint() }
    }

    private fun Position.toExternalPoint(): Point = Point(row = this.row.value - 1, col = this.column.value - 1)
}
