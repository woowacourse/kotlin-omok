package omock.model

import omock.model.rule.OMockRule

sealed class Player {
    abstract val stoneHistory: ArrayDeque<Stone>

    abstract val oMockRule: OMockRule

    fun turn(action: () -> Pair<String, String>): Stone {
        val coordinate = action()
        val row = Row(coordinate.second)
        val column = Column(coordinate.first)
        return generateStone(row, column)
    }

    private fun generateStone(
        row: Row,
        column: Column,
    ): Stone {
        return Stone.from(row = row, column = column)
    }

    abstract fun judgementResult(visited: Map<Direction, Result>): Boolean
}
