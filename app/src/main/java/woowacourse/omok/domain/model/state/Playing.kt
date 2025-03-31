package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.stone.BlackStones
import woowacourse.omok.domain.model.stone.StoneColor
import woowacourse.omok.domain.model.stone.WhiteStones

sealed class Playing : State() {
    abstract val blackStones: BlackStones
    abstract val whiteStones: WhiteStones

    abstract fun place(
        point: Point,
        boardSize: Int = Board.DEFAULT_BOARD_SIZE,
        onBoardUpdated: (BlackStones, WhiteStones) -> Unit,
    ): State

    abstract fun nextStoneColor(): StoneColor
}
