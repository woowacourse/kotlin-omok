package domain.domain

import domain.domain.CoordinateState.BLACK
import domain.domain.CoordinateState.WHITE
import domain.domain.ProgressState.CONTINUE
import domain.domain.ProgressState.END
import domain.domain.ProgressState.ERROR

class OmokGame(
    val board: Board = Board(),
    initTurn: CoordinateState = BLACK,
    private val gameRule: GameRule
) {
    var turn = initTurn
        private set

    private fun checkAddablePosition(position: Position): Boolean =
        gameRule.checkAddablePosition(board.boardState, turn, position)

    private fun checkWinner(position: Position): Boolean {
        return when (turn) {
            BLACK -> isBlackWin(position)
            WHITE -> isWhiteWin(position)
            else -> throw IllegalArgumentException(NOT_FIT_TURN_ERROR)
        }
    }

    private fun changeTurn() {
        turn = when (turn) {
            BLACK -> WHITE
            WHITE -> BLACK
            CoordinateState.EMPTY -> throw IllegalArgumentException()
        }
    }

    private fun isBlackWin(position: Position): Boolean =
        gameRule.isBlackWin(position, board.boardState)

    private fun isWhiteWin(position: Position): Boolean =
        gameRule.isWhiteWin(position, board.boardState)

    fun progressTurn(position: Position): ProgressState {
        if (determinateWinningProcess(position)) return END
        if (checkAddablePosition(position)) {
            board.addStone(turn, position)
            changeTurn()
        } else {
            return ERROR
        }
        return CONTINUE
    }

    private fun determinateWinningProcess(position: Position): Boolean {
        if (checkWinner(position)) {
            board.addStone(turn, position)
            return true
        }
        return false
    }

    companion object {
        private const val NOT_FIT_TURN_ERROR = "turn에 Black,White 가 아닌것이 포함되어있습니다."
    }
}
