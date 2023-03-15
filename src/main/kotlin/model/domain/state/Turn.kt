package model.domain.state

import model.domain.Board
import model.domain.Location
import model.domain.OmokRule
import model.domain.Stone
import model.domain.Stone.* // ktlint-disable no-wildcard-imports

abstract class Turn : State {
    abstract val stoneColor: Stone

    override fun place(location: Location, board: Board): State {
        if (!board.placeStone(location, stoneColor)) return retryTurn()
        if (OmokRule.isOmok(board, location, stoneColor)) return Omok()
        return BlackTurn()
    }

    override fun retry(): State {
        throw IllegalStateException("")
    }

    private fun retryTurn(): State {
        return when (stoneColor) {
            BLACK -> RetryBlackTurn()
            WHITE -> RetryWhiteTurn()
            EMPTY -> throw IllegalStateException("")
        }
    }
}
