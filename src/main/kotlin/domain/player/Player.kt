package domain.player

import domain.board.Board
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

abstract class Player(
    private val decidePosition: (latestStone: Stone?) -> Point,
    private val checkBoard: (currentBoard: Board) -> Unit,
) {

    abstract val color: Color

    open fun placeStone(currentBoard: Board): Board {
        checkBoard(currentBoard)

        while (true) {
            val placingPosition = decidePosition(currentBoard.latestStone)

            if (isPossibleToPlace(currentBoard, placingPosition)) {
                return Board(currentBoard.placedStones + Stone(placingPosition, color))
            }
        }
    }

    abstract fun isPossibleToPlace(board: Board, placingPosition: Point): Boolean
}
