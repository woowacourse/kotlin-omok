package omok.model

import rule.wrapper.point.Point

data class Position(
    val row: Row,
    val col: Col,
) {
    init {
        require(row.value in 0..14) { ROW_RANGE_ERROR_MESSAGE }
        require(col.value in 0..14) { COL_RANGE_ERROR_MESSAGE }
    }

    fun toPoint(): Point = Point(row.value, col.value)

    companion object {
        private const val ROW_RANGE_ERROR_MESSAGE = "가로 좌표는 오목판의 범위를 벗어날 수 없습니다"
        private const val COL_RANGE_ERROR_MESSAGE = "세로 좌표는 오목판의 범위를 벗어날 수 없습니다"
    }
}
