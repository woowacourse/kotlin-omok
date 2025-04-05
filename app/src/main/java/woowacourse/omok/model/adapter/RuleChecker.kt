package woowacourse.omok.model.adapter

import woowacourse.omok.model.Stone
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.gameState.GameState
import woowacourse.omok.model.position.Position

interface RuleChecker {
    fun canPut(
        board: Board,
        position: Position,
        stone: Stone,
    ): Boolean

    fun getState(
        board: Board,
        position: Position,
        stone: Stone,
    ): GameState
}
