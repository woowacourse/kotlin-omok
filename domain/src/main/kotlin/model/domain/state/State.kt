package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone

interface State {
    val stoneColor: Stone
    fun place(location: Location, board: Board): State
}
