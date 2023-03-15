package model.domain.state

import model.domain.Board
import model.domain.Location

class WhiteTurn(private val whiteBoard: Board) : State {
    override fun place(location: Location, board: Board): State {
        return WhiteTurn(whiteBoard)
    }
}
