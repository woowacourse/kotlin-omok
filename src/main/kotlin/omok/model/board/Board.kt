package omok.model.board

import omok.model.rule.OmokRuleJudge

class Board(
    size: BoardSize,
    points: Map<Point, StoneColor?> = emptyMap(),
    private val judge: OmokRuleJudge,
) {
    private val _points: MutableMap<Point, StoneColor?> = points.toMutableMap()
    val points: Map<Point, StoneColor?> get() = _points.toMap()

    val size: Int = size.value

    init {
        for (row in BoardSize.MIN_SIZE..size.value) {
            for (col in BoardSize.MIN_SIZE..size.value) {
                val key = Point(row, col)
                _points[key] = _points.getOrDefault(key, null)
            }
        }
    }

    fun findStoneColor(point: Point): StoneColor? = _points[point]

    fun placeStone(
        point: Point,
        color: StoneColor,
    ): PlaceStoneResult {
        if (checkOutOfBounds(point)) return PlaceStoneResult.Failure.InvalidPoint
        if (checkAlreadyPlaced(point)) return PlaceStoneResult.Failure.AlreadyPlaced

        if (!judge.validate(this, point, color)) return PlaceStoneResult.Failure.Closed
        updatePoint(point, color)

        if (judge.isWin(this, point, color)) return PlaceStoneResult.Success.Placed(point)
        return PlaceStoneResult.Success.Finished(point)
    }

    private fun updatePoint(
        point: Point,
        newColor: StoneColor,
    ) {
        _points[point] = newColor
    }

    private fun checkOutOfBounds(point: Point): Boolean {
        return !(listOf(point.x, point.y).all { it in BoardSize.MIN_SIZE..size })
    }

    private fun checkAlreadyPlaced(point: Point): Boolean {
        val color = findStoneColor(point)
        return color != null
    }
}
