package woowacourse.omok.domain.point

import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.Column
import woowacourse.omok.domain.board.OmokBoard.Companion.OMOK_BOARD_SIZE
import woowacourse.omok.domain.board.Row

class OmokPoints {
    private var points: List<Point> =
        (1..OMOK_BOARD_SIZE).flatMap { y ->
            (1..OMOK_BOARD_SIZE).map { x ->
                Point(Column(x), Row(y), BoardStatus.Empty)
            }
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
}
