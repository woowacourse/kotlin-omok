package omok.model.board

class BoardPoints(val size: BoardSize, points: Map<Point, StoneColor?> = emptyMap()) {
    private val _points: MutableMap<Point, StoneColor?> = points.toMutableMap()
    val points: Map<Point, StoneColor?> get() = _points.toMap()

    init {
        for (row in BoardSize.MIN_SIZE..size.value) {
            for (col in BoardSize.MIN_SIZE..size.value) {
                val key = Point(row, col)
                _points[key] = _points.getOrDefault(key, null)
            }
        }
    }

    fun getState(point: Point): StoneColor? = _points[point]

    fun update(
        point: Point,
        newState: StoneColor,
    ) {
        _points[point] = newState
    }
}
