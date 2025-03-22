package omok.model

import rule.BlackRenjuRule
import rule.WhiteRenjuRule
import rule.type.Violation
import rule.wrapper.point.Point

class Game2(val board: Board) {
    val blackPlayer = Player2(Color.BLACK)
    val whitePlayer = Player2(Color.WHITE)

    fun processTurn(position: Position) {
        when (board.lastStone?.color) {
            Color.BLACK -> {
                checkViolation(position, Color.WHITE)
                whitePlayer.makeMove(board, position)
            }
            Color.WHITE, null -> {
                checkViolation(position, Color.BLACK)
                blackPlayer.makeMove(board, position)
            }
        }
    }

    private fun checkViolation(
        position: Position,
        color: Color,
    ) {
        val blackPoints: List<Point> =
            board.stones
                .filter { stone -> stone.color == Color.BLACK }
                .map { stone -> Point(stone.position.x, stone.position.y) }
        val whitePoints: List<Point> =
            board.stones
                .filter { stone -> stone.color == Color.WHITE }
                .map { stone -> Point(stone.position.x, stone.position.y) }

        val newPoint = Point(position.x, position.y)
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
        private const val ERROR_MESSAGE_DOUBLE_THREE_VIOLATION = "삼삼 금수입니다."
        private const val ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION = "사사 금수입니다."
        private const val ERROR_MESSAGE_OVERLINE_VIOLATION = "장목 금수입니다."
    }
}
