package library.forbiddenthree

import domain.Coordinate
import domain.CoordinateState
import domain.CoordinateState.BLACK
import domain.CoordinateState.EMPTY
import domain.Position

object ThreeDummy {

    // 3   ? X X
    // 4   X
    // 5   X
    //     3 4 5
    fun getForbiddenThreeCoordinate1() = Position(Coordinate(3), Coordinate(3))
    fun getForbiddenThreeBoard1(): List<List<CoordinateState>> {
        val board = List(15) { MutableList(15) { EMPTY } }
        board[3][4] = BLACK
        board[3][5] = BLACK
        board[4][3] = BLACK
        board[5][3] = BLACK
        return board
    }

    // 3   ? X X
    // 4     X
    // 5       X
    //     3 4 5
    fun getForbiddenThreeCoordinate2() = Position(Coordinate(3), Coordinate(3))
    fun getForbiddenThreeBoard2(): List<List<CoordinateState>> {
        val board = List(15) { MutableList(15) { EMPTY } }
        board[3][4] = BLACK
        board[3][5] = BLACK
        board[4][4] = BLACK
        board[5][5] = BLACK
        return board
    }
}
