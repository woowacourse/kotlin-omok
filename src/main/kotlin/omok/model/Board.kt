package omok.model

import rule.BlackRenjuRule
import rule.WhiteRenjuRule
import rule.type.Violation
import rule.wrapper.point.Point

class Board {
    val stones: MutableSet<Stone2> = mutableSetOf()
    var lastStone: Stone2? = null

    fun add(newStone: Stone2) {
        require(
            !stones.map { stone -> stone.position }
                .contains(newStone.position),
        ) { ERROR_MESSAGE_POSITION_ALREADY_OCCUPIED }

        checkViolation(newStone)

        stones.add(newStone)
        lastStone = newStone
    }

    private fun checkViolation(newStone: Stone2) {
        val blackPoints: List<Point> =
            stones.filter {
                    stone ->
                stone.color == Color.BLACK
            }.map { stone -> Point(stone.position.x, stone.position.y) }
        val whitePoints: List<Point> =
            stones.filter {
                    stone ->
                stone.color == Color.WHITE
            }.map { stone -> Point(stone.position.x, stone.position.y) }
        val newPoint = Point(newStone.position.x, newStone.position.y)

        val violation: Violation =
            when (newStone.color) {
                Color.BLACK -> BlackRenjuRule().checkAnyFoulCondition(blackPoints, whitePoints, newPoint)
                Color.WHITE -> WhiteRenjuRule().checkAnyFoulCondition(whitePoints, blackPoints, newPoint)
            }

        require(violation == Violation.NONE) {
            when (violation) {
                Violation.DOUBLE_THREE -> ERROR_MESSAGE_DOUBLE_THREE_VIOLATION
                Violation.DOUBLE_FOUR -> ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION
                Violation.OVERLINE -> ERROR_MESSAGE_OVERLINE_VIOLATION
                Violation.NONE -> throw IllegalStateException()
            }
        }
    }

    companion object {
        private const val ERROR_MESSAGE_POSITION_ALREADY_OCCUPIED = "이미 돌이 있는 자리입니다."
        private const val ERROR_MESSAGE_DOUBLE_THREE_VIOLATION = "삼삼 금수입니다."
        private const val ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION = "사사 금수입니다."
        private const val ERROR_MESSAGE_OVERLINE_VIOLATION = "장목 금수입니다."
    }
}
