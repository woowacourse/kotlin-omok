package omok.model

import rule.wrapper.point.Point

class Points(
    points: List<Point> = listOf()
) {
    private var _points = points
    val points get() = _points.map { it.copy() }

    fun add(point: Point) {
        _points += point
    }
}