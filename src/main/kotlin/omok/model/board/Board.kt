package omok.model.board

import omok.model.rule.OmokJudge
import omok.model.stone.Position
import omok.model.stone.StoneColor

class Board {
    private val judge: OmokJudge = OmokJudge()
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
            PointState.OPEN -> handlePlaceSuccess(color, point)
            else -> PlaceStoneResult.AlreadyPlaced
        }
    }

    private fun handlePlaceSuccess(
        color: StoneColor,
        point: Point,
    ): PlaceStoneResult {
        if (color == StoneColor.BLACK && !judge.validate(this, point)) {
            return PlaceStoneResult.Closed
        }

        point.changeColor(color)
        return PlaceStoneResult.Success(point)
    }

    companion object {
        const val BOARD_MIN_SIZE = 1
        const val BOARD_MAX_SIZE = 15
    }
}
