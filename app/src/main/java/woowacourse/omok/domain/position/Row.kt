package woowacourse.omok.domain.position

class Row private constructor(
    val value: Int,
) {
    init {
        require(value in MIN_VALUE..MAX_VALUE) { ERROR_OUT_OF_BOUND }
    }

    fun isSame(other: Row): Boolean = this.value == other.value

    operator fun plus(step: Int): Row = Row(this.value + step)

    operator fun minus(step: Int): Row = Row(this.value - step)

    companion object {
        const val MIN_VALUE = 1
        const val MAX_VALUE = 15
        private const val ERROR_OUT_OF_BOUND = "유효하지 않은 행을 입력했습니다."

        fun from(value: Int): Row = Row(value)
    }
}
