package omock.model.turn

import omock.model.Column
import omock.model.Direction
import omock.model.Result
import omock.model.Row
import omock.model.rule.OMockRule
import omock.model.state.Stone

sealed class Turn {
    abstract val stoneHistory: ArrayDeque<Stone>

    abstract val oMockRule: OMockRule

    fun stoneHistoryAdd(stone: Stone) {
        stoneHistory.add(stone)
    }

    abstract fun isFinished(): Boolean

    fun turn(action: () -> Pair<String, String>): Stone {
        val coordinate = action()
        val row = Row(coordinate.second)
        val column = Column(coordinate.first)
        return generateStone(row, column)
    }

    fun turnOff(): Turn {
        return when (this) {
            is BlackTurn -> WhiteTurn(this.stoneHistory)
            is WhiteTurn -> BlackTurn(this.stoneHistory)
            is FinishedTurn -> FinishedTurn(this.stoneHistory)
        }
    }

    private fun generateStone(
        row: Row,
        column: Column,
    ): Stone {
        return Stone.from(row = row, column = column)
    }

    abstract fun judgementResult(visited: Map<Direction, Result>): Turn
}
