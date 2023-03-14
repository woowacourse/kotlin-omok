package model.domain

class WhiteTurn(private val whiteBoard: Board) : State {
    override fun place(location: Location, board: Board): State {
        return WhiteTurn(whiteBoard)
    }
}
