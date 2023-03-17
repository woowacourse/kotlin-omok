package domain

import domain.board.Board
import domain.board.PlayingBoard
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class OmokGame(
    val getPosition: (latestStone: Stone?) -> Position,
    val checkBoardState: (Board) -> Unit,
) {
    fun playOmokGameAndReturnWinner(): Color {
        var board: Board = PlayingBoard()
        var turnColor = Color.BLACK
        while (board.isFinished.not()) {
            checkBoardState(board)
            board = turnGame(board, turnColor)
            turnColor = !turnColor
        }
        checkBoardState(board)
        return board.winningColor
    }

    private tailrec fun turnGame(board: Board, color: Color): Board {
        val position = getPosition(board.getLatestStone())
        if (board.isPossiblePut(position)) {
            return board.putStone(Stone(position, color))
        }
        return turnGame(board, color)
    }
}
