package omok.model

import rule.Col
import rule.Row
import rule.wrapper.point.Point

object PointFactory {
    fun create(
        row: Row?,
        col: Col,
    ): Point {
        require(row != null && row in 1..BOARD_SIZE && col in 1..BOARD_SIZE) { ERROR_MESSAGE_POINT_NOT_IN_RANGE }
        return Point(row, col)
    }

    private const val ERROR_MESSAGE_POINT_NOT_IN_RANGE = "유효하지 않은 돌의 위치입니다."
    private const val BOARD_SIZE = 15
}
