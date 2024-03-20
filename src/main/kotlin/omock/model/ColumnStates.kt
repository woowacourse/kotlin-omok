package omock.model

data class ColumnStates(
    private val columnStates: MutableList<StoneState>
) {

    fun change(column: Int, player: Player) {
        columnStates[column] = getStoneState(column).put(player)
    }

    fun getStoneState(column: Int): StoneState {
        return columnStates[column]
    }
}
