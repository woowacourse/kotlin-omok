package omock.model

data class ColumnStates(
    private val columnStates: MutableList<StoneState>,
) {
    fun change(
        row: Int,
        player: Player,
    ) {
        columnStates[row] = getStoneState(row).put(player)
    }

    fun rollback(row: Int) {
        columnStates[row] = getStoneState(row).rollback()
    }

    fun getStoneState(column: Int): StoneState {
        return columnStates[column]
    }
}