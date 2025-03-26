package woowacourse.omok.model.board

import omok.model.rule.OmokRule
import omok.model.rule.OmokRuleManager
import woowacourse.omok.model.StoneColor

class Board(
    private val boardSize: BoardSize,
    private val rules: OmokRuleManager,
) {
    val points: List<Point> =
        (BOARD_MIN_SIZE..boardSize.value).flatMap { row ->
            (BOARD_MIN_SIZE..boardSize.value).map { col ->
                Point(row, col)
            }
        }

    val size = boardSize.value

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
        if (color == StoneColor.BLACK && !isForbiddenMove(point, rules.forbiddenMoveRule)) {
            return PlaceStoneResult.ForbiddenMove
        }

        point.changeState(color)

        return if (isOmok(point, rules.winningRule)) {
            PlaceStoneResult.Omok(point)
        } else {
            PlaceStoneResult.Success(point)
        }
    }

    private fun isForbiddenMove(
        point: Point,
        rules: List<OmokRule>,
    ): Boolean = rules.none { it.calculate(this, point) }

    private fun isOmok(
        point: Point,
        rule: OmokRule,
    ): Boolean = rule.calculate(this, point)

    companion object {
        const val BOARD_MIN_SIZE = 1
        private const val NOT_FOUND_POINT_ERROR_MESSAGE = "Point를 찾을 수 없습니다."
    }
}
