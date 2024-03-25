package omock.model

import omock.model.state.StoneState
import omock.model.turn.Turn

data class ColumnStates(
    private val columnStates: MutableList<StoneState>,
) {
    fun getStoneNumber(): List<Int> = columnStates.map { it.getNumber() }

    fun change(
        row: Int,
        turn: Turn,
    ) {
        columnStates[row] = getStoneState(row).put(turn)
    }

    fun rollback(row: Int) {
        columnStates[row] = getStoneState(row).rollback()
    }

    fun getStoneState(column: Int): StoneState {
        return columnStates[column]
    }
}
