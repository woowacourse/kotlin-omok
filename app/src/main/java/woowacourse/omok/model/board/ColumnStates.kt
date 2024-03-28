package woowacourse.omok.model.board

import woowacourse.omok.model.player.Player
import woowacourse.omok.model.stonestate.Clear
import woowacourse.omok.model.stonestate.StoneState

data class ColumnStates(
    private val columnStates: MutableList<StoneState>,
) {
    fun change(
        row: Int,
        player: Player,
    ) {
        val currentStone = getStoneState(row)
        require(currentStone is Clear) { ERROR_NOT_CLEAR }
        columnStates[row] = currentStone.put(player)
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
