package woowacourse.omok.controller

import woowacourse.omok.model.Board
import woowacourse.omok.model.game.GameState
import woowacourse.omok.model.game.ViolationResult
import woowacourse.omok.model.stone.Point
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor

class OmokAppController(
    private val board: Board = Board(),
) {
    fun place(stone: Stone) {
        board.place(stone)
    }

    fun stone(point: Point): Stone {
        val lastStone = board.stones.lastStone
        val nextColor: StoneColor = (lastStone?.color ?: StoneColor.WHITE).reverse()
        return Stone(point, nextColor)
    }

    fun violationResult(stone: Stone): ViolationResult? = board.checkViolation(stone)

    fun gameState(stone: Stone): GameState = board.gameState(stone)
}
