package omok.model

import omok.model.Position.Companion.INDEX_RANGE

fun initBoard(vararg stonePosition: StonePosition): Board {
    val board = INDEX_RANGE.flatMap { row ->
        INDEX_RANGE.map { col -> Position(row, col) }
    }.associateWith { Stone.NONE }
        .toMutableMap()

    board.forEach { (position, _) ->
        stonePosition.forEach {
            if (position == it.position) {
                board[position] = it.stone
            }
        }
    }
    return Board(board)
}

data class StonePosition(val position: Position, val stone: Stone)
