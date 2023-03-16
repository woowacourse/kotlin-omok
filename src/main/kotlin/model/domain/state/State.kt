package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Location

interface State {
    fun place(location: Location, board: Board): State

    fun retry(): State
}
