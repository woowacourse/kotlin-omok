package omok.model

data class Column private constructor(val value: Int) {
    companion object {
        val COORDINATE_COL_RANGE = 'A'..'O'
        const val ERROR_COLUMN_OUT_OF_RANGE = "열이 바둑판의 범위를 벗어났습니다."

        fun from(col: String): Column {
            require(col.first() in COORDINATE_COL_RANGE) {
                ERROR_COLUMN_OUT_OF_RANGE
            }
            return Column(col.first() - 'A' + 1)
        }
    }
}
