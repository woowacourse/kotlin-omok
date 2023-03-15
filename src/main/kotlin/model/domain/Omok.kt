package model.domain

class Omok(private val whiteBoard: Board) : State {
    override fun place(location: Location, board: Board): State {
        throw IllegalStateException("")
    }
}
