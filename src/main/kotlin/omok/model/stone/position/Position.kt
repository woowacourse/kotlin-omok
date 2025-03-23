package omok.model.stone.position

data class Position(
    val row: Row,
    val col: Col,
) {
    init {
        require(row.value >= MIN_RANGE) { ERROR_ROW_RANGE }
        require(col.value >= MIN_RANGE) { ERROR_COL_RANGE }
    }

    constructor(colAlphabetText: String, rowNumberText: String) : this(
        row = Row((rowNumberText.toIntOrNull() ?: throw IllegalArgumentException(ERROR_ROW_NUM)) - 1),
        col =
            Col(
                colAlphabetText[0].uppercaseChar().let {
                    require(it in MIN_COL_CHAR..MAX_COL_CHAR) { ERROR_COL_STRING.format(it) }
                    it - MIN_COL_CHAR
                },
            ),
    )

    constructor(coordinateText: String) : this(
        colAlphabetText = coordinateText[0].toString(),
        rowNumberText = (coordinateText.substring(1)),
    )

    companion object {
        private const val ERROR_ROW_RANGE = "가로 좌표는 오목판의 범위를 벗어날 수 없습니다"
        private const val ERROR_COL_RANGE = "세로 좌표는 오목판의 범위를 벗어날 수 없습니다"
        private const val ERROR_ROW_NUM = "행 번호가 유효하지 않습니다"
        private const val ERROR_COL_STRING = "열 문자가 유효하지 않습니다: %s"

        private const val MIN_COL_CHAR = 'A'
        private const val MAX_COL_CHAR = 'Z'
        private const val MIN_RANGE = 0
    }
}
