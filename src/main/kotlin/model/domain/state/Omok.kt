package model.domain.state

import model.domain.Board
import model.domain.Location

class Omok(private val whiteBoard: Board) : State {
    override fun place(location: Location, board: Board): State {
        throw IllegalStateException("")
    }
}
