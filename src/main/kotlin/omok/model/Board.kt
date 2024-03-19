package omok.model

class Board(
    private var stones: List<Stone> = emptyList(),
) {
    private val positions = stones.map { it.position }

    fun place(stone: Stone) {
        require(stone.position !in positions) { EXCEPTION_DUPLICATED_POSITION }
        stones = stones.plus(stone)
    }

    companion object {
        private const val EXCEPTION_DUPLICATED_POSITION = "중복된 곳에 착수할 수 없습니다."
    }
}
