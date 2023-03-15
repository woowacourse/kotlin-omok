package model.domain.state

import model.domain.Board
import model.domain.Location

interface State {
    fun place(location: Location, board: Board): State
}
