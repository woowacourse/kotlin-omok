package omok.model

fun initBoard(
    vararg stonePositions: StonePosition,
    size: Int = 15,
): Board {
    val board =
        boardSizeRange(size).flatMap { row ->
            boardSizeRange(size).map { col -> Position(row, col) }
        }.associateWith { Stone.NONE }
            .toMutableMap()

    board.forEach { (position, _) ->
        stonePositions.forEach {
            if (position == it.position) {
                board[position] = it.stone
            }
        }
    }
    return Board(size, board)
}

private fun boardSizeRange(size: Int) = 0..<size

data class StonePosition(val position: Position, val stone: Stone)
