package domain

import domain.CoordinateState.EMPTY
import domain.domain.BoardState

class Board(
    private val boardState: BoardState,
) {

    var lastPosition: Position? = null
        private set

    fun addStone(coordinateState: CoordinateState, position: Position) {
        boardState.addStone(coordinateState, position)
        lastPosition = position
    }

    fun isEmpty(position: Position): Boolean = boardState.value[position.coordinateY][position.coordinateX] == EMPTY

    companion object {
        const val BOARD_SIZE = 15
    }
}
