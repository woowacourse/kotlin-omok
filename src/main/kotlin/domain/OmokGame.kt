package domain

import domain.CoordinateState.BLACK
import domain.CoordinateState.WHITE

class OmokGame(val board: Board, initTurn: CoordinateState = BLACK) {
    var turn = initTurn
        private set

    private fun putStone(position: Position, addStoneState: Boolean) {
        if (addStoneState) board.addStone(turn, position)
    }

    private fun checkAddablePosition(position: Position): Boolean {
        if (!board.isEmpty(position)) return false
        if (turn == BLACK && isBlackForbidden(position)) return false
        return true
    }

    private fun checkWinner(position: Position): Boolean {
        return when (turn) {
            BLACK -> isBlackWin(position)
            WHITE -> isWhiteWin(position)
            else -> throw IllegalArgumentException()
        }
    }

    private fun changeTurn() {
        turn = when (turn) {
            BLACK -> WHITE
            WHITE -> BLACK
            CoordinateState.EMPTY -> throw IllegalArgumentException()
        }
    }

    private fun isBlackWin(position: Position): Boolean = board.isExactlyFive(position, turn)

    private fun isWhiteWin(position: Position): Boolean =
        board.isExactlyFive(position, turn) || board.isExceedFive(position, turn)

    private fun isBlackForbidden(position: Position): Boolean =
        board.isForbiddenThree(position) or board.isForbiddenFour(position) or board.isExceedFive(position, turn)

    fun progressTurn(transmitTurnState: (Board, CoordinateState) -> Position, transmitPutStoneState: () -> Unit) {
        while (true) {
            val position = transmitTurnState(board, turn)
            if (determinateWinningProcess(position)) break
            val success = checkAddablePosition(position)
            putStone(position, success)
            if (!success) transmitPutStoneState() else changeTurn()
        }
    }

    private fun determinateWinningProcess(position: Position): Boolean {
        if (checkWinner(position)) {
            board.addStone(turn, position)
            return true
        }
        return false
    }
}
