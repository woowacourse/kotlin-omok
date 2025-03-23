package omok.model.board

class BoardPoints(val size: BoardSize) {
    private val _points: MutableMap<Point, PointState> = mutableMapOf()
    val points: Map<Point, PointState> get() = _points.toMap()

    init {
        for (row in BoardSize.MIN_SIZE..size.value) {
            for (col in BoardSize.MIN_SIZE..size.value) {
                _points[Point(row, col)] = PointState.OPEN
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
