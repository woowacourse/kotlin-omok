package woowacourse.omok.model.board

import woowacourse.omok.model.GameState
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.stonestate.Clear
import woowacourse.omok.model.stonestate.StoneState

data class ColumnStates(
    private val columnStates: MutableList<StoneState>,
) {
    fun change(
        row: Int,
        player: Player,
    ): GameState.LoadStoneState {
        val currentStone = getStoneState(row)
        val stoneState = currentStone.put(player)
        if (currentStone is Clear) return GameState.LoadStoneState.Failure(Throwable(ERROR_NOT_CLEAR))
        return when(stoneState){
            is GameState.LoadStoneState.Success -> {
                columnStates[row] = stoneState.stoneState
                GameState.LoadStoneState.Success(currentStone)
            }
            is GameState.LoadStoneState.Failure -> stoneState
        }
    }

    fun rollback(row: Int) {
        columnStates[row] = getStoneState(row).rollback()
    }

    fun getStoneState(column: Int): StoneState {
        return columnStates[column]
    }

    companion object {
        private const val ERROR_NOT_CLEAR = "이미 돌이 있습니다."
    }
}
