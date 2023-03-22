package domain

import domain.domain.BoardState

class Board(
    val boardState: BoardState = BoardState(),
) {

    var lastPosition: Position? = null
        private set

    fun addStone(coordinateState: CoordinateState, position: Position) {
        boardState.addStone(coordinateState, position)
        lastPosition = position
    }

    companion object {
        const val BOARD_SIZE = 15
    }
}
