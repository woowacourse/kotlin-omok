package model.domain.state

import model.domain.Board
import model.domain.Location
import model.domain.OmokRule
import model.domain.Stone

abstract class Turn : State {
    abstract val stoneColor: Stone
    abstract val retryTurn: RetryTurn
    abstract val omok: Omok
    abstract val turn: Turn

    override fun place(location: Location, board: Board): State {
        if (!board.placeStone(location, stoneColor)) return retryTurn
        if (OmokRule.isOmok(board, location, stoneColor)) return omok
        return turn
    }

    override fun retry(): State {
        throw IllegalStateException("")
    }
}
