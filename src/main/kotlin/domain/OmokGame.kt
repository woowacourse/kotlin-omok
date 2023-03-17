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

    /**
     * TODO: run game이라는 함수 이름으로 이것이 승자 color를 반환한다는 것을 알 수 있을까?
     */
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

    /**
     * TODO: initial try의 개념이 필요할까?
     */
    fun turnGame(board: Board, color: Color, initialTry: Boolean = true): Board {
        val position = getPosition(board.getLatestStone(), initialTry)
        if (!board.isPossiblePut(position)) {
            return turnGame(board, color, false)
        }
        return board.putStone(Stone(position, color))
    }
}
