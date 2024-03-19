package omok.model

class Coordinate(val row: Int, val col: String) {
    init {
        require(row in COORDINATE_ROW_RANGE && col in COORDINATE_COL_RANGE) {
            COORDINATE_ERROR_OUT_OF_RANGE
        }
    }

    companion object {
        val COORDINATE_ROW_RANGE = 1..15
        val COORDINATE_COL_RANGE = "A".."O"
        const val COORDINATE_ERROR_OUT_OF_RANGE = "바둑판의 범위를 벗어났습니다."
    }
}
