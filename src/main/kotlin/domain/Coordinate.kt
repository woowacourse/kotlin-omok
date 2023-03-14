package domain

@JvmInline
value class Coordinate(private val value: Int) {
    init {
        validateRange()
    }

    private fun validateRange() {
        require(value in COORDINATE_RANGE) { "잘못된 좌표값입니다. 현재 입력된 좌표: $value" }
    }

    companion object {
        private val COORDINATE_RANGE = 0..14
    }
}
