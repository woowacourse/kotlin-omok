package domain.player

import domain.board.Board
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

abstract class Player {

    abstract val color: Color

    fun placeStone(
        currentBoard: Board,
        decidedPoint: Point
    ): Board{

        if(isPossibleToPlace(currentBoard, decidedPoint)){
            return Board(currentBoard.placedStones + Stone(decidedPoint, color))
        }
        return currentBoard
    }

    abstract fun isPossibleToPlace(board: Board, placingPoint: Point): Boolean
}
