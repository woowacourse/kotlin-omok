package woowacourse.omok.domain.point

import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.Column
import woowacourse.omok.domain.board.OmokBoard.Companion.OMOK_BOARD_SIZE
import woowacourse.omok.domain.board.Row
import woowacourse.omok.domain.exception.OmokExceptions
import woowacourse.omok.domain.stone.StoneColor

class OmokPoints {
    private var points: List<Point> = makePoints()

    val movedPoints
        get() =
            points
                .filterNot { it.status == BoardStatus.Empty }

    fun combine(newPoints: List<Point>) {
        val updatedPoints =
            points.map { existingPoint ->
                newPoints.find { it.x == existingPoint.x && it.y == existingPoint.y }
                    ?: existingPoint
            } + newPoints.filter { np -> points.none { it.x == np.x && it.y == np.y } }

        points = updatedPoints
    }

    fun clear() {
        points = makePoints()
    }

    fun pointValidation(point: Point) {
        blocked(point)
        occupied(point)
    }

    fun getPointAt(
        row: Row,
        column: Column,
    ): Point {
        return points.find { it.x == column && it.y == row } ?: Point.WALL
    }

    fun toList(): List<Point> = points.toList()

    fun moveStone(point: Point) {
        val position = points.indexOfFirst { it.x == point.x && it.y == point.y }
        val newList = points.toMutableList()
        newList[position] = point
        points = newList
    }

    private fun occupied(point: Point) {
        val target = points.find { it.x == point.x && it.y == point.y }?.status
        require(target == BoardStatus.Empty) {
            throw OmokExceptions.OccupiedExceptions
        }
    }

    private fun blocked(point: Point) {
        if (point.status == BoardStatus.Moved(StoneColor.WHITE)) return
        val target = points.first { it.x == point.x && it.y == point.y }.status
        if (target is BoardStatus.Blocked) {
            throw target.cause
        }
    }

    private fun makePoints(): List<Point> {
        return (1..OMOK_BOARD_SIZE).flatMap { y ->
            (1..OMOK_BOARD_SIZE).map { x ->
                Point(Column(x), Row(y), BoardStatus.Empty)
            }
        }
    }
}
