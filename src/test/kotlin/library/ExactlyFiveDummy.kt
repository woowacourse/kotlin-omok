package library

import domain.Coordinate
import domain.CoordinateState
import domain.Position

object ExactlyFiveDummy {
    // 3
    // 4 X X X ? X
    //   3 4 5 6 7
    fun getExactlyFiveCoordinate() = Position(Coordinate(4), Coordinate(6))
    fun getExactlyFiveBoard(coordinateState: CoordinateState): List<MutableList<CoordinateState>> {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[4][3] = coordinateState
        board[4][4] = coordinateState
        board[4][5] = coordinateState
        board[4][7] = coordinateState

        return board
    }
}
