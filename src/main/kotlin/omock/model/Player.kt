package omock.model

sealed interface Player {
    fun generateStone(row: Row, column: Column): Stone {
        return Stone.from(row = row, column = column)
    }

    fun judgementResult(): Boolean
}
