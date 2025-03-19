package omok.model.board

import omok.model.stone.Position
import omok.model.stone.StoneColor

class Board {
    val points: List<Point> =
        (BOARD_MIN_SIZE..BOARD_MAX_SIZE).flatMap { row ->
            (BOARD_MIN_SIZE..BOARD_MAX_SIZE).map { col ->
                Point(Position(row, col))
            }
        }

    fun findPoint(position: Position): Point? {
        return points.find { it.position == position }
    }

    fun placeStone(
        position: Position,
        color: StoneColor,
    ): PlaceStoneResult {
        val point = findPoint(position)
        val state = point?.state
        return when (state) {
            PointState.OPEN -> {
                point.changeColor(color)
                PlaceStoneResult.Success(point)
            }
            PointState.CLOSED -> PlaceStoneResult.Closed
            else -> PlaceStoneResult.AlreadyPlaced
        }
    }

    companion object {
        const val BOARD_MIN_SIZE = 1
        const val BOARD_MAX_SIZE = 15
    }
}
