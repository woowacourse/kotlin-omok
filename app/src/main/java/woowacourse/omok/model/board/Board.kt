package woowacourse.omok.model.board

import woowacourse.omok.model.board.result.Finished
import woowacourse.omok.model.board.result.OnGoing
import woowacourse.omok.model.board.result.PlaceStoneResult
import woowacourse.omok.model.rule.RuleValidator

class Board(
    size: BoardSize,
    points: Map<Point, StoneColor?> = emptyMap(),
    private val validator: RuleValidator,
) {
    private val _points: MutableMap<Point, StoneColor?> = points.toMutableMap()
    val points: Map<Point, StoneColor?> get() = _points.toMap()

    val size: Int = size.value

    constructor(size: BoardSize, judge: RuleValidator) : this(size, emptyMap(), judge)

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
        if (checkOutOfBounds(point)) return OnGoing.InvalidMove
        if (checkAlreadyPlaced(point)) return OnGoing.AlreadyPlaced
        if (validator.checkViolation(this, point, color)) return OnGoing.RuleViolation

        return handleStonePlacement(point, color)
    }

    private fun handleStonePlacement(
        point: Point,
        color: StoneColor,
    ): PlaceStoneResult {
        updatePoint(point, color)

        if (validator.checkWinCondition(this, point, color)) return Finished.GameFinished(point)
        return checkBoardStatus(point)
    }

    private fun checkBoardStatus(point: Point): PlaceStoneResult {
        if (points.count { it.value == null } == 0) return Finished.BoardFull(point)
        return OnGoing.StonePlaced(point)
    }

    private fun updatePoint(
        point: Point,
        newColor: StoneColor,
    ) {
        _points[point] = newColor
    }

    private fun checkOutOfBounds(point: Point): Boolean = !(listOf(point.x, point.y).all { it in BoardSize.MIN_SIZE..size })

    private fun checkAlreadyPlaced(point: Point): Boolean = findStoneColor(point) != null
}
