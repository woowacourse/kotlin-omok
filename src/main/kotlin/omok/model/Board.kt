package omok.model

class Board(
    stones: List<Stone> = emptyList(),
) {
    private val positions = stones.map { it.position }
    var stones = stones
        private set

    fun place(position: Position) {
        require(position !in positions) { EXCEPTION_DUPLICATED_POSITION }
        val stonesCount = stones.size
        if (isEven(stonesCount)) {
            stones = stones.plus(Stone.Black(position))
        }
        if (!isEven(stonesCount)) {
            stones = stones.plus(Stone.White(position))
        }
    }

    private fun isEven(num: Int): Boolean {
        return num % ODD_EVEN_INDICATOR == 0
    }

    companion object {
        private const val EXCEPTION_DUPLICATED_POSITION = "중복된 곳에 착수할 수 없습니다."
        private const val ODD_EVEN_INDICATOR = 2
    }
}
