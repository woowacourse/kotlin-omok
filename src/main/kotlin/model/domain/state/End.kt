package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone

class End(override val board: Board, override val stone: Stone) : State {
    override fun place(location: Location): State {
        throw IllegalStateException("")
    }
}
