package omok.model

data class Coordinate(val x: Int, val y: Int) {
    init {
        validatePosition(x, y)
    }

    override fun toString(): String {
        val xChar = BASE_CHAR + this.x
        val yNum = this.y + BASE_NUMBER
        return "$xChar$yNum"
    }

    companion object {
        private const val BASE_CHAR = 'A'
        private const val BASE_NUMBER = 1
        private const val MINIMUM_RANGE = 0
        private const val MAXIMUM_RANGE = 14
        private const val DIVIDE_INDEX = 1
        private val POSITION_RANGE = MINIMUM_RANGE..MAXIMUM_RANGE
        private const val ERROR_INVALID_POSITION = "유회하지 않은 위치입니다."
        private const val ERROR_EMPTY_INPUT = "입력값이 필요합니다."

        private fun validatePosition(
            x: Int,
            y: Int,
        ) {
            require(x in POSITION_RANGE && y in POSITION_RANGE) {
                ERROR_INVALID_POSITION
            }
        }

        fun from(position: String?): Coordinate {
            val input = position?.validateEmpty() ?: throw IllegalArgumentException(ERROR_EMPTY_INPUT)
            val x = input.first().uppercaseChar() - BASE_CHAR
            val y =
                input.substring(DIVIDE_INDEX).toIntOrNull()?.minus(BASE_NUMBER) ?: throw IllegalArgumentException(
                    ERROR_EMPTY_INPUT,
                )
            return Coordinate(x, y)
        }

        private fun String.validateEmpty(): String {
            require(this.isNotEmpty()) { ERROR_EMPTY_INPUT }
            return this
        }
    }
}
