package omok.model.game

import omok.model.external.rule.wrapper.point.Point
import omok.model.stone.Coordinate
import omok.model.stone.GoStoneColor

abstract class OmokRuleAdapter {
    abstract fun checkWin(coordinate: Coordinate): PlacementState

    fun Coordinate.toPoint() = Point(this.x, this.y)

    fun Board.getStonePoints(color: GoStoneColor): List<Point> =
        this.board.flatten()
            .filterNotNull()
            .filter { it.color == color }
            .map { Point(it.coordinate.x, it.coordinate.y) }
}
