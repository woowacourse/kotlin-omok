package omok.model

class Position private constructor(val format: String, val coordinate: Coordinate) {
    companion object {
        private const val BASE_CHAR = 'A'
        private const val BASE_NUMBER = 1
        private const val CHAR_DIGIT = 1
        private const val MINIMUM_RANGE = 0
        private const val MAXIMUM_RANGE = 14
        const val ERROR_INVALID_POSITION = "유효하지 않은 위치입니다."
        const val ERROR_EMPTY_INPUT = "입력값이 필요합니다."

        fun from(input: String): Position {
            input.validateEmpty()
            val x = input.first().uppercaseChar() - BASE_CHAR
            val y =
                input.drop(CHAR_DIGIT).toIntOrNull()?.minus(BASE_NUMBER) ?: throw IllegalArgumentException(
                    ERROR_INVALID_POSITION,
                )
            return Position(input, Coordinate(x.validateRange(), y.validateRange()))
        }

        private fun Int.validateRange(): Int {
            require(this in MINIMUM_RANGE..MAXIMUM_RANGE) { ERROR_INVALID_POSITION }
            return this
        }

        private fun String.validateEmpty(): String {
            require(this.isNotEmpty()) { ERROR_EMPTY_INPUT }
            return this
        }
    }

    fun copy(
        format: String = this.format,
        coordinate: Coordinate = this.coordinate,
    ): Position {
        return Position(format, coordinate.copy())
    }
}
