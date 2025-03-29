package woowacourse.omok.controller

import woowacourse.omok.model.Board
import woowacourse.omok.model.game.GameState
import woowacourse.omok.model.game.ViolationResult
import woowacourse.omok.model.stone.Stone

class OmokAppController(
    private val board: Board = Board(),
) {
    fun place(stone: Stone) {
        board.place(stone)
    }

    fun violationResult(stone: Stone): ViolationResult? = board.checkViolation(stone)

    fun gameState(stone: Stone): GameState = board.gameState(stone)
}
