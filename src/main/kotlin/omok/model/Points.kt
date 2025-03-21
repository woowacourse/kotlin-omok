package omok.model

class Points(
    points: Set<Point> = setOf(),
) {
    private var _points = points
    val points get() = _points.map { it.copy() }

    fun add(point: Point) {
        _points += point
    }
}
