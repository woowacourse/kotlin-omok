package omok.model

import rule.OmokRule
import rule.WhiteRenjuRule
import rule.type.Violation
import rule.wrapper.point.Point

class WhitePlayer : Player() {
    override val points = mutableListOf<Point>()

    override val rule: OmokRule = WhiteRenjuRule()

    override fun place(
        newPoint: Point,
        otherPoints: List<Point>,
    ) {
        require(!isOccupied(newPoint, otherPoints)) { ERROR_MESSAGE_IS_ALREADY_OCCUPIED }
        checkViolation(newPoint, otherPoints)
        points += newPoint
    }

    companion object {
        private const val ERROR_MESSAGE_IS_ALREADY_OCCUPIED = "이미 돌이 있는 자리입니다."
        private const val ERROR_MESSAGE_DOUBLE_THREE_VIOLATION = "삼삼 금수입니다."
        private const val ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION = "사사 금수입니다."
        private const val ERROR_MESSAGE_OVERLINE_VIOLATION = "장목 금수입니다."
    }
}
