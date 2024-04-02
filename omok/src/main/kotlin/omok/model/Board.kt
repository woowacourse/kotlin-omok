package omok.model

import omok.model.Row.Companion.MAX_ROW
import omok.model.state.Clear
import omok.model.state.Stone
import omok.model.turn.Turn

class Board(val stoneStates: List<ColumnStates>) {
    fun setStoneState(
        turn: Turn,
        stone: Stone,
    ): Result<Unit> {
        val row = stone.row.getIndex()
        val column = stone.column.getIndex()
        return stoneStates[row].change(column, turn)
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
