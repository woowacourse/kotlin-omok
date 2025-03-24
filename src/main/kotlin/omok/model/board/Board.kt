package omok.model.board

import omok.model.StoneColor
import omok.model.rule.ForbiddenMoveJudge
import omok.model.rule.count.OmokCountRule

class Board(private val boardSize: BoardSize) {
    val points: List<Point> =
        (BOARD_MIN_SIZE..boardSize.value).flatMap { row ->
            (BOARD_MIN_SIZE..boardSize.value).map { col ->
                Point(row, col)
            }
        }

    val size = boardSize.value

    fun findPoint(point: Point): Point {
        return points.find { it == point } ?: throw IllegalArgumentException(NOT_FOUND_POINT_ERROR_MESSAGE)
    }

    fun placeStone(
        point: Point,
        color: StoneColor,
        rule: OmokCountRule,
    ): PlaceStoneResult {
        val targetPoint = findPoint(point)

        return when (targetPoint.state) {
            PointState.OPEN -> handlePlaceSuccess(color, targetPoint, rule)
            else -> PlaceStoneResult.AlreadyPlaced
        }
    }

    private fun handlePlaceSuccess(
        color: StoneColor,
        point: Point,
        rule: OmokCountRule,
    ): PlaceStoneResult {
        if (color == StoneColor.BLACK && !ForbiddenMoveJudge.validate(this, point)) {
            return PlaceStoneResult.ForbiddenMove
        }

        point.changeState(color)

        return if (isOmok(point, rule)) {
            PlaceStoneResult.Omok(point)
        } else {
            PlaceStoneResult.Success(point)
        }
    }

    private fun isOmok(
        point: Point,
        rule: OmokCountRule,
    ): Boolean {
        return rule.calculate(this, point)
    }

    companion object {
        const val BOARD_MIN_SIZE = 1
        private const val NOT_FOUND_POINT_ERROR_MESSAGE = "Point를 찾을 수 없습니다."
    }
}
