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
    ): MoveResult {
        (checkViolation(position, color) as? MoveResult.Fail)?.let { moveResult -> return moveResult }
        (board.add(Stone(position, color)) as? MoveResult.Fail)?.let { moveResult -> return moveResult }
        lastStone = Stone(position, color)
        return checkOmok(position, color)
    }

    private fun checkOmok(
        position: Position,
        color: Color,
    ): MoveResult {
        val rule: OmokRule =
            when (color) {
                Color.BLACK -> BlackRenjuRule(board.col, board.row)
                Color.WHITE -> WhiteRenjuRule(board.col, board.row)
            }
        val points: List<Point> = board.filterStones(color).map { stone -> Point(stone.position.x, stone.position.y) }
        val newPoint = Point(position.x, position.y)
        val isOmok: Boolean = rule.checkSerialSameStonesBiDirection(points, newPoint, OMOK_CONDITION)
        if (!isOmok) return MoveResult.Success.Playing
        return when (color) {
            Color.BLACK -> MoveResult.Success.BlackWin
            Color.WHITE -> MoveResult.Success.WhiteWin
        }
    }

    private fun checkViolation(
        position: Position,
        color: Color,
    ): MoveResult {
        val newPoint = Point(position.x, position.y)
        val blackPoints: List<Point> =
            board.filterStones(Color.BLACK).map { stone -> Point(stone.position.x, stone.position.y) }
        val whitePoints: List<Point> =
            board.filterStones(Color.WHITE).map { stone -> Point(stone.position.x, stone.position.y) }
        val violation: Violation =
            when (color) {
                Color.BLACK ->
                    BlackRenjuRule(board.col, board.row).checkAnyFoulCondition(blackPoints, whitePoints, newPoint)

                Color.WHITE ->
                    WhiteRenjuRule(board.col, board.row).checkAnyFoulCondition(whitePoints, blackPoints, newPoint)
            }

        return when (violation) {
            Violation.DOUBLE_THREE -> MoveResult.Fail.DoubleThreeViolation
            Violation.DOUBLE_FOUR -> MoveResult.Fail.DoubleFourViolation
            Violation.OVERLINE -> MoveResult.Fail.OverlineViolation
            Violation.NONE -> MoveResult.Success.Playing
        }
    }

    companion object {
        private const val OMOK_CONDITION = 5
    }
}
