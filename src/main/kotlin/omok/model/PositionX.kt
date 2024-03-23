package omok.model

data class PositionX(val value: Int) {
    init {
        require(value in COORDINATE_ROW_RANGE) {
            ERROR_ROW_OUT_OF_RANGE
        }
    }

    companion object {
        val COORDINATE_ROW_RANGE = 1..15
        const val ERROR_ROW_OUT_OF_RANGE = "행이 바둑판의 범위를 벗어났습니다."
    }
}
