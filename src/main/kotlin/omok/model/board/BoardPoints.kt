package omok.model.board

class BoardPoints(val size: BoardSize, points: Map<Point, PointState> = emptyMap()) {
    private val _points: MutableMap<Point, PointState> = points.toMutableMap()
    val points: Map<Point, PointState> get() = _points.toMap()

    init {
        for (row in BoardSize.MIN_SIZE..size.value) {
            for (col in BoardSize.MIN_SIZE..size.value) {
                val key = Point(row, col)
                _points[key] = _points.getOrDefault(key, PointState.OPEN)
            }
        }
    }

    fun getState(point: Point): PointState? = _points[point]

    fun update(
        point: Point,
        newState: PointState,
    ) {
        _points[point] = newState
    }
}
