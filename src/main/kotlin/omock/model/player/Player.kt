package omock.model.player

import omock.model.position.Column
import omock.model.position.Row
import omock.model.search.Direction
import omock.model.search.DirectionResult
import omock.model.search.VisitedDirectionResult
import omock.model.stone.Stone

sealed class Player {
    abstract val stoneHistory: ArrayDeque<Stone>

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

    abstract fun judgementResult(visitedDirectionResult: VisitedDirectionResult): Boolean
}
