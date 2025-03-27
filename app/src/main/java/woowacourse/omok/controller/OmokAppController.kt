package woowacourse.omok.controller

import woowacourse.omok.model.Board
import woowacourse.omok.model.game.FoulConditionResult
import woowacourse.omok.model.game.GameState
import woowacourse.omok.model.game.InvalidMoveResult
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

    fun foulConditionResult(stone: Stone): FoulConditionResult? = board.checkFoulCondition(stone)

    fun invalidMoveResult(stone: Stone): InvalidMoveResult? = board.checkInvalidMove(stone)

    fun gameState(stone: Stone): GameState = board.gameState(stone)
}
