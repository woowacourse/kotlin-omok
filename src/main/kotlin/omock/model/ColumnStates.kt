package omock.model

import omock.model.ErrorType.AlreadyExistStone
import omock.model.state.StoneState
import omock.model.turn.Turn

data class ColumnStates(
    private val columnStates: MutableList<StoneState>,
) {
    fun getStoneNumber(): List<Int> = columnStates.map { it.getNumber() }

    fun change(
        row: Int,
        turn: Turn,
    ): Result<Unit> {
        getStoneState(row).put(turn).onSuccess { stoneState ->
            columnStates[row] = stoneState
            return Result.success(Unit)
        }.onFailure { e ->
            return Result.failure(e)
        }
        return Result.failure(AlreadyExistStone())
    }

    fun rollback(row: Int) {
        columnStates[row] = getStoneState(row).rollback()
    }

    fun getStoneState(column: Int): StoneState {
        return columnStates[column]
    }
}
