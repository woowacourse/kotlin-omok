package woowacourse.omok.model.board

import omok.model.rule.OmokRuleManager
import woowacourse.omok.database.SavedStone
import woowacourse.omok.model.StoneColor
import woowacourse.omok.model.board.BoardSize.Companion.BOARD_MIN_SIZE

class Board(
    private val boardSize: BoardSize,
    private val rules: OmokRuleManager,
    savedStones: List<SavedStone> = emptyList(),
) {
    val points: List<Point> =
        (BOARD_MIN_SIZE..boardSize.value).flatMap { row ->
            (BOARD_MIN_SIZE..boardSize.value).map { col ->
                Point(row, col)
            }
        }

    val size = boardSize.value

    init {
        savedStones.forEach { (x, y, color) ->
            val point = Point(x, y)
            findPoint(point).changeState(color)
        }
    }

    fun findPoint(point: Point): Point =
        points.find { it == point } ?: throw IllegalArgumentException(
            NOT_FOUND_POINT_ERROR_MESSAGE,
        )

    fun placeStone(
        point: Point,
        color: StoneColor,
    ): PlaceStoneResult {
        val targetPoint = findPoint(point)

        return when (targetPoint.state) {
            PointState.OPEN -> handlePlaceSuccess(color, targetPoint)
            else -> PlaceStoneResult.AlreadyPlaced
        }
    }

    private fun handlePlaceSuccess(
        color: StoneColor,
        point: Point,
    ): PlaceStoneResult {
        if (color == StoneColor.BLACK && !isForbiddenMove(point)) {
            return PlaceStoneResult.ForbiddenMove
        }

        point.changeState(color)

        return if (isOmok(point)) {
            PlaceStoneResult.Omok(point)
        } else {
            PlaceStoneResult.Success(point)
        }
    }

    private fun isForbiddenMove(point: Point): Boolean = rules.forbiddenMoveRule.none { it.calculate(this, point) }

    private fun isOmok(point: Point): Boolean = rules.winningRule.calculate(this, point)

    companion object {
        private const val NOT_FOUND_POINT_ERROR_MESSAGE = "Point를 찾을 수 없습니다."
    }
}
