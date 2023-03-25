package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Location

abstract class Omok : State {
    override fun place(location: Location, board: Board): State {
        throw IllegalStateException("")
    }
}
