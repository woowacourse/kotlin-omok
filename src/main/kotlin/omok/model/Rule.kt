package omok.model

object Rule {
    private const val BOARD_SIZE = 15

    fun isWinCondition(
        board: List<List<StoneType?>>,
        stone: Stone,
    ): Boolean {
        val column = stone.point.column
        val row = stone.point.row
        val stoneType = stone.type

        return (
            checkDirection(board, column, row, stoneType, 1, 0) ||
                checkDirection(board, column, row, stoneType, 0, 1) ||
                checkDirection(board, column, row, stoneType, 1, 1) ||
                checkDirection(board, column, row, stoneType, 1, -1)
        )
    }

    private fun checkDirection(
        board: List<List<StoneType?>>,
        column: Int,
        row: Int,
        stoneType: StoneType,
        directionColumn: Int,
        directionRow: Int,
    ): Boolean {
        var count = 0
        for (i in -4..4) {
            val targetColumn = column + i * directionColumn
            val targetRow = row + i * directionRow
            if (targetColumn !in 0 until BOARD_SIZE || targetRow !in 0 until BOARD_SIZE) continue
            if (board[targetColumn][targetRow] == stoneType) {
                count++
                if (count >= 5) return true
            } else {
                count = 0
            }
        }
        return false
    }
}
