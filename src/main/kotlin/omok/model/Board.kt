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
        if (stonesCount % 2 == 0) {
            stones = stones.plus(BlackStone(position))
        }
        if (stonesCount % 2 == 1) {
            stones = stones.plus(WhiteStone(position))
        }
    }

    companion object {
        private const val EXCEPTION_DUPLICATED_POSITION = "중복된 곳에 착수할 수 없습니다."
    }
}
