package omok.model.stone.position

import rule.wrapper.point.Point

data class Position(
    val row: Row,
    val col: Col,
) {
    init {
        require(row.value in MIN_RANGE..MAX_RANGE) { ERROR_ROW_RANGE }
        require(col.value in MIN_RANGE..MAX_RANGE) { ERROR_COL_RANGE }
    }

    constructor(coordinateText: String) : this(
        row = Row((coordinateText.substring(1).toIntOrNull() ?: throw IllegalArgumentException(ERROR_ROW_NUM)) - 1),
        col =
            Col(
                coordinateText[0].uppercaseChar().let {
                    require(it in MIN_COL_CHAR..MAX_COL_CHAR) { ERROR_COL_STRING.format(it) }
                    it - MIN_COL_CHAR
                },
            ),
    )

    fun toPoint(): Point = Point(row.value, col.value)

    companion object {
        private const val ERROR_ROW_RANGE = "가로 좌표는 오목판의 범위를 벗어날 수 없습니다"
        private const val ERROR_COL_RANGE = "세로 좌표는 오목판의 범위를 벗어날 수 없습니다"
        private const val ERROR_ROW_NUM = "행 번호가 유효하지 않습니다"
        private const val ERROR_COL_STRING = "열 문자가 유효하지 않습니다: %s"

        private const val MIN_COL_CHAR = 'A'
        private const val MAX_COL_CHAR = 'O'
        private const val MIN_RANGE = 0
        private const val MAX_RANGE = 14
    }
}
