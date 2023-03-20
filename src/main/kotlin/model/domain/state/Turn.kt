package model.domain.state

import model.domain.rule.OmokRule
import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone

abstract class Turn(override val board: Board) : State {
    override fun place(location: Location): State {
        if (isForbidden(location)) return this
        board.placeStone(location, stone)
        if (isOmok(location)) return End(board, stone)
        return nextTurn()
    }

    private fun isOmok(location: Location): Boolean = OmokRule.isOmok(board, location, stone)

    private fun nextTurn() = when (stone) {
        Stone.BLACK -> WhiteTurn(board)
        Stone.WHITE -> BlackTurn(board)
        else -> throw IllegalStateException()
    }

    abstract fun isForbidden(location: Location): Boolean
}
