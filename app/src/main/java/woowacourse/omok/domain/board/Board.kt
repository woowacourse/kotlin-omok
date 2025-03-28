package woowacourse.omok.domain.board

import woowacourse.omok.domain.board.result.Finished
import woowacourse.omok.domain.board.result.OnGoing
import woowacourse.omok.domain.board.result.PlaceStoneResult
import woowacourse.omok.domain.rule.RuleValidator

class Board(
    size: BoardSize,
    points: Map<Point, CellState> = emptyMap(),
    private val validator: RuleValidator,
) {
    private val _cells: MutableMap<Point, CellState> = points.toMutableMap()
    val cells: Map<Point, CellState> get() = _cells.toMap()

    val size: Int = size.value

    constructor(size: BoardSize, judge: RuleValidator) : this(size, emptyMap(), judge)

    init {
        for (row in BoardSize.MIN_SIZE..size.value) {
            for (col in BoardSize.MIN_SIZE..size.value) {
                val key = Point(row, col)
                _cells[key] = _cells.getOrDefault(key, CellState.EMPTY)
            }
        }
    }

    fun findStoneColor(point: Point): CellState? = _cells[point]

    fun placeStone(
        point: Point,
        color: CellState,
    ): PlaceStoneResult {
        if (checkOutOfBounds(point)) return OnGoing.InvalidMove
        if (checkAlreadyPlaced(point)) return OnGoing.AlreadyPlaced
        if (validator.checkViolation(this, point, color)) return OnGoing.RuleViolation

        return handleStonePlacement(point, color)
    }

    private fun handleStonePlacement(
        point: Point,
        color: CellState,
    ): PlaceStoneResult {
        updateCell(point, color)

        if (validator.checkWinCondition(this, point, color)) return Finished.GameFinished(point)
        return checkBoardStatus(point)
    }

    private fun checkBoardStatus(point: Point): PlaceStoneResult {
        if (cells.count { it.value == CellState.EMPTY } == 0) return Finished.BoardFull(point)
        return OnGoing.StonePlaced(point)
    }

    private fun updateCell(
        point: Point,
        newColor: CellState,
    ) {
        _cells[point] = newColor
    }

    private fun checkOutOfBounds(point: Point): Boolean = !(listOf(point.x, point.y).all { it in BoardSize.MIN_SIZE..size })

    private fun checkAlreadyPlaced(point: Point): Boolean = findStoneColor(point) != CellState.EMPTY
}
