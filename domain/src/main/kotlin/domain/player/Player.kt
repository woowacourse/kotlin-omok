package domain.player

import domain.board.Board
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

abstract class Player {

    abstract val color: Color
    protected var state: PlayerState = PlayerState.Placing

    fun placeStone(
        currentBoard: Board,
        checkBoard: (Board) -> Unit,
        decidePoint: (latestStone: Stone?, currentColor: Color) -> Point
    ): Board {
        checkBoard(currentBoard)
        val point = decidePoint(currentBoard.latestStone, color)

        if (isPossibleToPlace(currentBoard, point)) {
            state = PlayerState.Waiting
            return Board(currentBoard.placedStones + Stone(point, color))
        }
        state = PlayerState.Placing
        return currentBoard
    }

    abstract fun toNextPlayer(): Player

    abstract fun isPossibleToPlace(board: Board, placingPoint: Point): Boolean
}
