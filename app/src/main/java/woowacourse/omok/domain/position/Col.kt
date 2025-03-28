package woowacourse.omok.domain.position

class Col private constructor(
    val value: Int,
) {
    init {
        require(value in MIN_VALUE..MAX_VALUE) { ERROR_OUT_OF_BOUND }
    }

    fun isSame(other: Col): Boolean = value == other.value

    operator fun plus(step: Int): Col = Col(this.value + step)

    operator fun minus(step: Int): Col = Col(this.value - step)

    companion object {
        const val MIN_VALUE = 1
        const val MAX_VALUE = 15
        private const val ERROR_OUT_OF_BOUND = "유효하지 않은 열을 입력했습니다."
        private const val ASCII_A_OFFSET = 64

        fun from(char: Char): Col = Col(char.toGridCol())

        fun from(int: Int): Col = Col(int)

        private fun Char.toGridCol(): Int = this.code - ASCII_A_OFFSET
    }
}
