package model

class Row private constructor(
    val value: Int,
) {
    init {
        require(value in GameBoard.ROW_RANGE) { ERROR_OUT_OF_BOUND }
    }

    fun isSame(other: Row): Boolean = this.value == other.value

    operator fun plus(step: Int): Row = Row(this.value + step)

    operator fun minus(step: Int): Row = Row(this.value - step)

    fun isMax(): Boolean = value == GameBoard.ROW_RANGE.last

    fun isMin(): Boolean = value == GameBoard.ROW_RANGE.first

    companion object {
        private const val ERROR_OUT_OF_BOUND = "입력한 행이 범위를 벗어났습니다."

        fun from(value: Int): Row = Row(value)
    }
}
