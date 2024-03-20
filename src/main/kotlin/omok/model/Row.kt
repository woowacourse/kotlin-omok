package omok.model

data class Row private constructor(val value: Int) {
    companion object {
        val COORDINATE_ROW_RANGE = 1..15
        const val ERROR_ROW_OUT_OF_RANGE = "행이 바둑판의 범위를 벗어났습니다."

        fun from(row: String): Row {
            val rowValue = row.toIntOrNull()
            requireNotNull(rowValue) {
                "행의 입력은 문자가 아닌 정수이어야 합니다"
            }
            require(rowValue in COORDINATE_ROW_RANGE) {
                ERROR_ROW_OUT_OF_RANGE
            }
            return Row(rowValue)
        }
    }
}
