package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Location

abstract class End(override val board: Board) : State {
    override fun place(location: Location): State {
        throw IllegalStateException("")
    }
}
