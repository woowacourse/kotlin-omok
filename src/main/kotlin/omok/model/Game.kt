package omok.model

import rule.BlackRenjuRule
import rule.WhiteRenjuRule
import rule.type.Violation
import rule.wrapper.point.Point

class Game(val board: Board) {
    var lastStone: Stone? = null
        private set

    fun processTurn(
        position: Position,
        color: Color,
    ): GameState {
        checkViolation(position, color)
        board.add(Stone(position, color))
        lastStone = Stone(position, color)
        val isOmok: Boolean =
            when (color) {
                Color.BLACK ->
                    BlackRenjuRule().checkSerialSameStonesBiDirection(
                        board.filterStones(color).map {
                                stone ->
                            Point(stone.position.x, stone.position.y)
                        },
                        Point(position.x, position.y),
                        OMOK_CONDITION,
                    )
                Color.WHITE ->
                    WhiteRenjuRule().checkSerialSameStonesBiDirection(
                        board.filterStones(color).map {
                                stone ->
                            Point(stone.position.x, stone.position.y)
                        },
                        Point(position.x, position.y),
                        OMOK_CONDITION,
                    )
            }
        return when (isOmok) {
            true -> GameState.FINISHED
            false -> GameState.PLAYING
        }
    }

    fun chooseTurn(): Color {
        return when (lastStone?.color) {
            Color.BLACK -> Color.WHITE
            Color.WHITE, null -> Color.BLACK
        }
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
