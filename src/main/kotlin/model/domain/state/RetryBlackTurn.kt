package model.domain.state

import model.domain.Board
import model.domain.Location

class RetryBlackTurn : State {
    override fun place(location: Location, board: Board): State {
        throw IllegalStateException("")
    }

    override fun retry(): State {
        return BlackTurn()
    }
}
