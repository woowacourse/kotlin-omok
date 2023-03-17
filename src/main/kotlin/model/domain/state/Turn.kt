package model.domain.state

import model.domain.rule.OmokRule
import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone

abstract class Turn : State {
    abstract val stoneColor: Stone
    abstract val retryTurn: RetryTurn
    abstract val omok: Omok
    abstract val turn: Turn

    override fun place(location: Location, board: Board): State {
        val y = location.coordinationX.value
        val x = location.coordinationY.value

        return when {
            isForbidden(board, x, y) -> retryTurn
            !board.placeStone(location, stoneColor) -> retryTurn
            OmokRule.isOmok(board, location, stoneColor) -> omok
            else -> turn
        }
    }

    abstract fun isForbidden(board: Board, x: Int, y: Int): Boolean

    override fun retry(): State {
        throw IllegalStateException("")
    }
}
