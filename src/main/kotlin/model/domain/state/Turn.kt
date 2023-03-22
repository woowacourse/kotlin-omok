package model.domain.state

import model.domain.rule.OmokRule
import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone

abstract class Turn : State {
    abstract val omok: Omok
    abstract val turn: Turn
    abstract val nextTurn: State
    abstract override val stoneColor: Stone

    override fun place(location: Location, board: Board): State = when {
        !canPlaceStone(location, board) -> turn
        OmokRule.isOmok(board, location, stoneColor) -> omok
        else -> nextTurn
    }

    abstract fun isForbidden(board: Board, location: Location): Boolean

    private fun canPlaceStone(location: Location, board: Board): Boolean {
        if (isForbidden(board, location)) return false
        if (!board.canPlaceStone(location, stoneColor)) return false
        return true
    }
}
