package woowacourse.omok.model.board

import woowacourse.omok.model.Stone

sealed class BoardCellState {
    data object Empty : BoardCellState()

    data class Exist(
        val stone: Stone,
    ) : BoardCellState()
}
