package domain.player

import domain.board.Board
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

abstract class Player() {

    abstract val color: Color

    fun placeStone(
        currentBoard: Board,
        checkBoard: (currentBoard: Board) -> Unit,
        decidePosition: (latestStone: Stone?) -> Point,
    ): Board {
        checkBoard(currentBoard)

        while (true) {
            val placingPosition = decidePosition(currentBoard.latestStone)

            if (isPossibleToPlace(currentBoard, placingPosition)) {
                return Board(currentBoard.placedStones + Stone(placingPosition, color))
            }
        }
    }

    open fun isPossibleToPlace(board: Board, placingPosition: Point): Boolean {

        return board.isPlaced(placingPosition)
    }
}
