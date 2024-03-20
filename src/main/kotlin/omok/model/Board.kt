package omok.model

import omok.model.search.AscendingDfs
import omok.model.search.DescendingDfs
import omok.model.search.HorizontalDfs
import omok.model.search.VerticalDfs

class Board(
    stones: List<Stone> = emptyList(),
) {
    val status: Array<Array<Color?>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { null } }

    var stones: List<Stone> = stones.toList()
        private set

    fun place(position: Position): GameResult? {
        require(position !in stones.map { it.position }) {
            EXCEPTION_DUPLICATED_POSITION
        }
        val stonesCount = stones.size
        val row = BOARD_SIZE - position.row.value
        val col = position.col.value
        when (isEven(stonesCount)) {
            true -> {
                addStone(row, position.col.title, Color.BLACK)
                stones = stones.plus(Stone.Black(Position.of(position.row.value, position.col.title)))
                if (calculateSearchResult(row, col, Color.BLACK)) {
                    return GameResult.WINNER_BLACK
                }
            }

            false -> {
                addStone(row, position.col.title, Color.WHITE)
                stones = stones.plus(Stone.White(Position.of(position.row.value, position.col.title)))
                if (calculateSearchResult(row, col, Color.WHITE)) {
                    return GameResult.WINNER_WHITE
                }
            }
        }
        if (stones.size >= 225) return GameResult.DRAW
        return null
    }

    private fun addStone(
        row: Int,
        col: Char,
        color: Color,
    ) {
        status[row][Column.valueOf(col)?.value ?: return] = color
    }

    private fun calculateSearchResult(
        row: Int,
        col: Int,
        color: Color,
    ): Boolean {
        val verticalCount = VerticalDfs(status).apply { search(color, row, col) }.count
        val horizontalCount = HorizontalDfs(status).apply { search(color, row, col) }.count
        val ascendingCount = AscendingDfs(status).apply { search(color, row, col) }.count
        val descendingCount = DescendingDfs(status).apply { search(color, row, col) }.count
        return listOf(verticalCount, horizontalCount, ascendingCount, descendingCount).any { it >= 5 }
    }

    private fun isEven(num: Int): Boolean {
        return num % ODD_EVEN_INDICATOR == 0
    }

    companion object {
        private const val EXCEPTION_DUPLICATED_POSITION = "중복된 곳에 착수할 수 없습니다."
        private const val ODD_EVEN_INDICATOR = 2
        private const val BOARD_SIZE = 16
    }
}
