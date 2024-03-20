package omok.model

class Board(
    stones: List<Stone> = emptyList(),
) {
    private val status =
        Array(BOARD_SIZE) {
            Array<Color?>(BOARD_SIZE) { null }
        }

    var stones: List<Stone> = stones.toList()
        private set

    fun place(position: Position): Array<Array<Color?>> {
        require(status[position.row.value - 1][position.col.value - 1] == null) {
            EXCEPTION_DUPLICATED_POSITION
        }
        val stonesCount = stones.size
        val row = BOARD_SIZE - position.row.value
        val col = position.col.value - 1
        when (isEven(stonesCount)) {
            true -> {
                addStone(row, position.col.title - 1, Color.BLACK)
                dfs(row, col, Color.BLACK)
            }
            false -> {
                addStone(row, position.col.title - 1, Color.WHITE)
                dfs(row, col, Color.WHITE)
            }
        }

        return status
    }

    private fun addStone(
        row: Int,
        col: Char,
        color: Color,
    ) {
        status[row][Column.valueOf(col)?.value ?: return] = color
        stones = stones.plus(Stone.Black(Position.of(row, col)))
    }

    private fun dfs(
        row: Int,
        col: Int,
        color: Color,
    ) {
        VerticalDfs(status).apply { search(color, row, col) }
        HorizontalDfs(status).apply { search(color, row, col) }
        AscendingDfs(status).apply { search(color, row, col) }
        DescendingDfs(status).apply { search(color, row, col) }
    }

    private fun isEven(num: Int): Boolean {
        return num % ODD_EVEN_INDICATOR == 0
    }

    companion object {
        private const val EXCEPTION_DUPLICATED_POSITION = "중복된 곳에 착수할 수 없습니다."
        private const val ODD_EVEN_INDICATOR = 2
        private const val BOARD_SIZE = 15
    }
}

// fun main() {
//    val board = Board()
//    board.place(Position(Row.ONE, Column.E))
//    board.place(Position(Row.ONE, Column.N))
//    board.place(Position(Row.TWO, Column.E))
//    board.place(Position(Row.TWO, Column.N))
//    board.place(Position(Row.THREE, Column.E))
//    board.place(Position(Row.FIVE, Column.N))
//    board.place(Position(Row.FOUR, Column.E))
//    board.place(Position(Row.TEN, Column.N))
//    val currentStatus = board.place(Position(Row.FIVE, Column.E))
//
//    currentStatus.forEach {
//        it.forEach { stone ->
//            print(stone)
//            print(" ")
//        }
//        println()
//    }
// }
