package omok.model

import rule.BlackRenjuRule
import rule.OmokRule
import rule.WhiteRenjuRule
import rule.type.Violation
import rule.wrapper.point.Point

class Game(val board: Board) {
    var lastStone: Stone? = null
        private set

    fun chooseTurn(): Color {
        return when (lastStone?.color) {
            Color.BLACK -> Color.WHITE
            Color.WHITE, null -> Color.BLACK
        }
    }

    fun processTurn(
        position: Position,
        color: Color,
    ): GameState {
        checkViolation(position, color)
        board.add(Stone(position, color))
        lastStone = Stone(position, color)
        return when (checkOmok(position, color)) {
            true -> GameState.FINISHED
            false -> GameState.PLAYING
        }
    }

    private fun checkOmok(
        position: Position,
        color: Color,
    ): Boolean {
        val rule: OmokRule =
            when (color) {
                Color.BLACK -> BlackRenjuRule()
                Color.WHITE -> WhiteRenjuRule()
            }
        val points: List<Point> = board.filterStones(color).map { stone -> Point(stone.position.x, stone.position.y) }
        val newPoint = Point(position.x, position.y)
        return rule.checkSerialSameStonesBiDirection(points, newPoint, OMOK_CONDITION)
    }

    private fun checkViolation(
        position: Position,
        color: Color,
    ) {
        val newPoint = Point(position.x, position.y)
        val blackPoints: List<Point> = board.filterStones(Color.BLACK).map { stone -> Point(stone.position.x, stone.position.y) }
        val whitePoints: List<Point> = board.filterStones(Color.WHITE).map { stone -> Point(stone.position.x, stone.position.y) }
        val violation: Violation =
            when (color) {
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
        private const val OMOK_CONDITION = 5
        private const val ERROR_MESSAGE_DOUBLE_THREE_VIOLATION = "삼삼 금수입니다."
        private const val ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION = "사사 금수입니다."
        private const val ERROR_MESSAGE_OVERLINE_VIOLATION = "장목 금수입니다."
    }
}
