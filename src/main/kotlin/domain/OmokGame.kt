package domain

import domain.board.Board
import domain.board.PlayingBoard
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class OmokGame(
    val getPosition: (Stone?, Boolean) -> Position,
    val checkBoardState: (Board) -> Unit,
) {
    fun runGame(): Color {
        var board: Board = PlayingBoard()
        var color = Color.BLACK
        while (board.isFinished.not()) {
            checkBoardState(board)
            board = turnGame(board, color)
            color = !color
        }
        checkBoardState(board)
        return board.winningColor
    }

    fun turnGame(board: Board, color: Color, initialTry: Boolean = true): Board {
        val position = getPosition(board.getLatestStone(), initialTry)
        return if (board.isPossiblePut(position)) {
            val nextBoard = board.putStone(Stone(position, color))
            nextBoard
        } else {
            turnGame(board, color, false)
        }
    }
}
