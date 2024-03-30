package woowacourse.omok.model.game

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone

interface OmokTurnAction {
    fun nextStonePosition(
        nowOrderStone: Stone,
        recentPosition: Position?,
    ): Position

    fun onStonePlace(board: Board)
}
