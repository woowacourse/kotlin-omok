package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone

interface State {
    val stone: Stone
    val board: Board
    fun place(location: Location): State
}
