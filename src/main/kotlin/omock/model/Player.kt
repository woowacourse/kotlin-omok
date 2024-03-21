package omock.model

sealed class Player {
    abstract val stoneHistory: ArrayDeque<Stone>

    fun turn(action: () -> Pair<String, String>): Stone {
        val coordinate = action()
        val row = Row(coordinate.second)
        val column = Column(coordinate.first)
        return generateStone(row, column)
    }

    private fun generateStone(row: Row, column: Column): Stone {
        return Stone.from(row = row, column = column)
    }

    abstract fun judgementResult(visited: Map<Direction, Result>): Boolean

    companion object {
        const val INIT_COUNT = 0
        const val MIN_FOUR_TO_FOUR_COUNT = 4
        const val MIN_IS_CLEAR_FOUR_TO_FOUR_COUNT = 4
        const val MIN_THREE_TO_THREE_COUNT = 4
        const val MIN_REVERSE_COUNT = 0
        const val MIN_O_MOCK_COUNT = 4
        const val EDGE_THREE_TO_THREE_COUNT = 2
        const val EDGE_FOUR_TO_FOUR_COUNT = 3
    }
}
