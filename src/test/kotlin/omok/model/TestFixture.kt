package omok.model

fun initBoard(vararg stonePosition: StonePosition): Board {
    val board =
        (0..14).flatMap { row ->
            (0..14).map { col -> Position(row, col) }
        }.associateWith { Stone.NONE }
            .toMutableMap()

    board.forEach { (position, _) ->
        stonePosition.forEach {
            if (position == it.position) {
                board[position] = it.stone
            }
        }
    }
    return Board(15, board)
}

data class StonePosition(val position: Position, val stone: Stone)
