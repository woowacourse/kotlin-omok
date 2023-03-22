package omok.model.game

import omok.model.external.rule.BlackRenjuRule
import omok.model.external.rule.WhiteRenjuRule
import omok.model.external.rule.type.Violation
import omok.model.external.rule.wrapper.point.Point
import omok.model.stone.Coordinate
import omok.model.stone.GoStoneColor

class OmokRuleAdapter(private val board: Board) {
    private val blackRenjuRule: BlackRenjuRule = BlackRenjuRule(board.sizeX, board.sizeY)
    private val whiteRenjuRule: WhiteRenjuRule = WhiteRenjuRule(board.sizeX, board.sizeY)

    fun checkWin(coordinate: Coordinate, color: GoStoneColor): PlacementState {
        return when (color) {
            GoStoneColor.BLACK ->
                if (blackRenjuRule.checkWin(
                        board.getStonesCoordinate(GoStoneColor.BLACK),
                        board.getStonesCoordinate(GoStoneColor.WHITE),
                        coordinate.toPoint()
                    )
                ) PlacementState.WIN else PlacementState.STAY

            GoStoneColor.WHITE ->
                if (whiteRenjuRule.checkWin(
                        board.getStonesCoordinate(GoStoneColor.WHITE),
                        board.getStonesCoordinate(GoStoneColor.BLACK),
                        coordinate.toPoint()
                    )
                ) PlacementState.WIN else PlacementState.STAY
        }
    }

    fun checkBlackAnyViolation(coordinate: Coordinate): PlacementState {
        val violation = blackRenjuRule.checkAnyFoulCondition(
            blackPoints = board.getStonesCoordinate(GoStoneColor.BLACK),
            whitePoints = board.getStonesCoordinate(GoStoneColor.WHITE),
            startPoint = coordinate.toPoint()
        )

        return when (violation) {
            Violation.OVERLINE -> PlacementState.LONG_LINE
            Violation.DOUBLE_FOUR -> PlacementState.OPEN_DOUBLE_FOUR
            Violation.DOUBLE_THREE -> PlacementState.OPEN_DOUBLE_THREE
            Violation.NONE -> PlacementState.STAY
        }
    }

    private fun Coordinate.toPoint(): Point {
        val x = this.x
        val y = this.y
        return Point(x, y)
    }

    companion object {
        private fun Board.getStonesCoordinate(color: GoStoneColor): List<Point> =
            this.board.flatten()
                .filterNotNull()
                .filter { it.color == color }
                .map { Point(it.coordinate.x, it.coordinate.y) }
    }
}
