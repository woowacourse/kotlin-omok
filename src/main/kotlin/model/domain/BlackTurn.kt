package model.domain

class BlackTurn(private val blackBoard: Board): State {
    override fun place(location: Location, board: Board): State {
        return BlackTurn(blackBoard)
    }
}