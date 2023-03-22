package domain

data class Position(val coordinateY: Int, val coordinateX: Int) {

    init {
        validateRange(coordinateY, coordinateX)
    }

    private fun validateRange(vararg value: Int) {
        value.forEach { require(it in COORDINATE_RANGE) { "잘못된 좌표값입니다. 현재 입력된 좌표: $value" } }
    }

    companion object {
        private val COORDINATE_RANGE = 0 until Board.BOARD_SIZE
    }
}
