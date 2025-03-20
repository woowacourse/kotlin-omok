package omok.model.board

import omok.model.StoneColor
import omok.model.rule.ForbiddenMoveJudge

class Board {
    val points: List<Point> =
        (BOARD_MIN_SIZE..BOARD_MAX_SIZE).flatMap { row ->
            (BOARD_MIN_SIZE..BOARD_MAX_SIZE).map { col ->
                Point(Position(row, col))
            }
        }

    fun findPoint(position: Position): Point {
        return requireNotNull(points.find { it.position == position }) { NOT_FOUND_POINT_ERROR_MESSAGE }
    }

    fun placeStone(
        position: Position,
        color: StoneColor,
    ): PlaceStoneResult {
        val point = findPoint(position)
        val state = point.state
        return when (state) {
            PointState.OPEN -> handlePlaceSuccess(color, point)
            else -> PlaceStoneResult.AlreadyPlaced
        }
    }

    private fun handlePlaceSuccess(
        color: StoneColor,
        point: Point,
    ): PlaceStoneResult {
        if (color == StoneColor.BLACK && !ForbiddenMoveJudge.validate(this, point)) {
            return PlaceStoneResult.Closed
        }

        point.changeColor(color)
        return PlaceStoneResult.Success(point)
    }

    companion object {
        const val BOARD_MIN_SIZE = 1
        const val BOARD_MAX_SIZE = 15

        private const val NOT_FOUND_POINT_ERROR_MESSAGE = "Point를 찾을 수 없습니다."
    }
}
