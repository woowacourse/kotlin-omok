package library.forbiddenfour

import domain.Coordinate
import domain.CoordinateState
import domain.Position

object FourDummy {
    // 3   ? X X X
    // 4   X
    // 5   X
    // 6   X
    //     3 4 5 6
    val forbiddenFourCoordinate1 = Position(Coordinate(3), Coordinate(3))
    fun getForbiddenFourBoard1(): List<List<CoordinateState>> {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[3][4] = CoordinateState.BLACK
        board[3][5] = CoordinateState.BLACK
        board[3][6] = CoordinateState.BLACK
        board[4][3] = CoordinateState.BLACK
        board[5][3] = CoordinateState.BLACK
        board[6][3] = CoordinateState.BLACK
        return board
    }

    // 3   ? X X X
    // 4     X
    // 5       X
    // 6         X
    //     3 4 5 6
    val forbiddenFourCoordinate2 = Position(Coordinate(3), Coordinate(3))
    fun getForbiddenFourBoard2(): List<List<CoordinateState>> {
        val board = List(15) { MutableList(15) { CoordinateState.EMPTY } }
        board[3][4] = CoordinateState.BLACK
        board[3][5] = CoordinateState.BLACK
        board[3][6] = CoordinateState.BLACK
        board[4][4] = CoordinateState.BLACK
        board[5][5] = CoordinateState.BLACK
        board[6][6] = CoordinateState.BLACK
        return board
    }
}
