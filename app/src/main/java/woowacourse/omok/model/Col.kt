package woowacourse.omok.model

data class Col private constructor(
    val value: Int,
) {
    init {
        require(value in GameBoard.colRange) { ERROR_OUT_OF_BOUND }
    }

    operator fun plus(step: Int): Col = Col(this.value + step)

    operator fun minus(step: Int): Col = Col(this.value - step)

    fun isMax(): Boolean = value == GameBoard.colRange.last

    fun isMin(): Boolean = value == GameBoard.colRange.first


    override fun toString(): String {
        return (value+ ASCII_A_OFFSET).toChar().toString()
    }

        companion object {
        private const val ERROR_OUT_OF_BOUND = "입력한 열이 범위를 벗어났습니다."
        const val ASCII_A_OFFSET = 64

        fun from(char: Char): Col = Col(char.toGridCol())

        fun from(int: Int): Col = Col(int)

        private fun Char.toGridCol(): Int = this.code - ASCII_A_OFFSET


    }
}
