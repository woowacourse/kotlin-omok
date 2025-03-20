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

    constructor(coordinateText: String) : this(
        row = Row((coordinateText.substring(1).toIntOrNull() ?: throw IllegalArgumentException("행 번호가 유효하지 않습니다")) - 1),
        col =
            Col(
                coordinateText[0].uppercaseChar().let {
                    require(it in MIN_COL_CHAR..MAX_COL_CHAR) { "열 문자가 유효하지 않습니다: $it" }
                    it - 'A'
                },
            ),
    )

    fun toPoint(): Point = Point(row.value, col.value)

    companion object {
        private const val ROW_RANGE_ERROR_MESSAGE = "가로 좌표는 오목판의 범위를 벗어날 수 없습니다"
        private const val COL_RANGE_ERROR_MESSAGE = "세로 좌표는 오목판의 범위를 벗어날 수 없습니다"

        private const val MIN_COL_CHAR = 'A'
        private const val MAX_COL_CHAR = 'O'
        private const val MIN_ROW = 1
        private const val MAX_ROW = 15
    }
}
