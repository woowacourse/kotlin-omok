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
            val placingPosition = decidePoint(currentBoard.latestStone)

            if (isPossibleToPlace(currentBoard, placingPosition)) {
                return Board(currentBoard.placedStones + Stone(placingPosition, color))
            }
        }
    }

    open fun isPossibleToPlace(board: Board, placingPosition: Point): Boolean {

        return !board.isPlaced(placingPosition)
    }
}
