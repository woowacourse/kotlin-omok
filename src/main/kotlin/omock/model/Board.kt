package omock.model

import omock.model.Row.Companion.MAX_ROW
import omock.model.state.Clear
import omock.model.state.Stone
import omock.model.turn.Turn

class Board(val stoneStates: List<ColumnStates>) {
    fun setStoneState(
        turn: Turn,
        stone: Stone,
    ) {
        val row = stone.row.getIndex()
        val column = stone.column.getIndex()
        stoneStates[row].change(column, turn)
    }

    fun rollbackState(stone: Stone) {
        val row = stone.row.getIndex()
        val column = stone.column.getIndex()
        stoneStates[row].rollback(column)
    }

    companion object {
        fun from(): Board {
            return Board(
                stoneStates =
                    Stone.stones.chunked(MAX_ROW).map { stones ->
                        ColumnStates(
                            stones.map {
                                Clear(it)
                            }.toMutableList(),
                        )
                    },
            )
        }
    }
}
