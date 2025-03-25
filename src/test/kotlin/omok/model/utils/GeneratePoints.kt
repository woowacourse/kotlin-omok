package omok.model.utils

import omok.model.board.BoardSize
import omok.model.board.Point
import omok.model.board.StoneColor
import omok.view.toPosition

fun generatePoints(
    points: Map<String, StoneColor?>,
    size: Int = 15,
): Map<Point, StoneColor?> {
    val newPoints =
        points.map { (rawPoint, color) ->
            val point = rawPoint.toPosition()
            Point(point.first, point.second) to color
        }.toMap().toMutableMap()

    for (row in BoardSize.MIN_SIZE..size) {
        for (col in BoardSize.MIN_SIZE..size) {
            val key = Point(row, col)
            newPoints[key] = newPoints.getOrDefault(key, null)
        }
    }

    return newPoints
}

fun String.toPoint(): Point {
    return this.toPosition().run { Point(first, second) }
}
