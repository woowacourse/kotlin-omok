package omok.model.board

import omok.model.rule.ForbiddenMoveJudge

class Board(points: Map<Point, PointState> = mutableMapOf(), val size: Int = DEFAULT_BOARD_SIZE) {
    var points: MutableMap<Point, PointState> = points.toMutableMap()
        private set

    init {
        require(size in DEFAULT_BOARD_SIZE..BOARD_MAX_SIZE) { INVALID_BOARD_SIZE }

        this.points =
            (BOARD_MIN_SIZE..size).flatMap { row ->
                (BOARD_MIN_SIZE..size).map { col ->
                    Point(row, col)
                }
            }.associateWith { point ->
                points[point] ?: PointState.OPEN
            }.toMutableMap()
    }

    fun findPoint(point: Point): Pair<Point, PointState>? {
        return points.entries.find { it.key == point }?.toPair()
    }

    fun placeStone(
        point: Point,
        pointState: PointState,
    ): PlaceStoneResult {
        return when (val state = findPoint(point)?.second) {
            PointState.OPEN -> handlePlaceSuccess(point, pointState)
            else -> handlePlaceFailure(state)
        }
    }

    private fun handlePlaceSuccess(
        point: Point,
        pointState: PointState,
    ): PlaceStoneResult {
        if (pointState == PointState.BLACK && !ForbiddenMoveJudge.validate(this, point)) {
            return PlaceStoneResult.Closed
        }

        changePointState(point, pointState)
        return PlaceStoneResult.Success(point)
    }

    private fun handlePlaceFailure(state: PointState?): PlaceStoneResult {
        if (state == null) return PlaceStoneResult.InvalidPoint

        return PlaceStoneResult.AlreadyPlaced
    }

    private fun changePointState(
        point: Point,
        newState: PointState,
    ) {
        points[point] = newState
    }

    companion object {
        const val BOARD_MIN_SIZE = 1
        private const val BOARD_MAX_SIZE = 25
        private const val DEFAULT_BOARD_SIZE = 15
        private const val INVALID_BOARD_SIZE = "유효하지 않은 바둑판 사이즈 입니다. ($DEFAULT_BOARD_SIZE ~ $BOARD_MAX_SIZE)"
    }
}
