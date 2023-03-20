package domain.player

import domain.board.Board
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

abstract class Player {

    abstract val color: Color

    fun placeStone(
        currentBoard: Board,
        checkBoard: (currentBoard: Board) -> Unit,
        decidePoint: (latestStone: Stone?) -> Point,
    ): Board {
        while (true) {
            checkBoard(currentBoard)
            val point = decidePoint(currentBoard.latestStone)

            if (isPossibleToPlace(currentBoard, point)) {
                return Board(currentBoard.placedStones + Stone(point, color))
            }
        }
    }

    abstract fun isPossibleToPlace(board: Board, placingPoint: Point): Boolean
}
