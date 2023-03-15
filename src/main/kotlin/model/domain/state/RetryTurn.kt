package model.domain.state

import model.domain.Board
import model.domain.Location

abstract class RetryTurn : State {
    override fun place(location: Location, board: Board): State {
        throw IllegalStateException("")
    }
}
