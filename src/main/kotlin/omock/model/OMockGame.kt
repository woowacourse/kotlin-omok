package omock.model

import omock.model.state.Stone
import omock.model.turn.BlackTurn
import omock.model.turn.Turn
import kotlin.Result

class OMockGame(
    private var turn: Turn = BlackTurn(),
    private val board: Board = Board.from(),
) {
    fun playGame(
        showBoard: (Turn) -> Stone?,
        error: (Throwable) -> Unit,
    ) {
        while (!turn.isFinished()) {
            showBoard(turn)?.let { playerStone ->
                executePlayerTurn(playerStone, error)
            }
        }
    }

    private fun executePlayerTurn(
        playerStone: Stone,
        error: (Throwable) -> Unit,
    ) {
        playerTurn(playerStone).onSuccess {
            turn.stoneHistoryAdd(playerStone)
        }.onFailure { e ->
            handleTurnFailure(playerStone, e)
            error(e)
        }
    }

    private fun playerTurn(playerStone: Stone): Result<Unit> {
        return runCatching {
            board.setStoneState(turn, playerStone)
            updateBoard(playerStone)

            val row = playerStone.row.getIndex()
            val column = playerStone.column.getIndex()

            turn = turn.processTurn(board.stoneStates.map { it.getStoneNumber() }, row, column)
        }
    }

    private fun updateBoard(playerStone: Stone) {
        val row = playerStone.row.toIntIndex() - 1
        val column = playerStone.column.getIndex()
        OMockBoard.boardTable[row][column] = Stone.getStoneIcon(turn)
    }

    private fun handleTurnFailure(
        playerStone: Stone,
        error: Throwable,
    ) {
        if (error is IllegalArgumentException) {
            board.rollbackState(playerStone)
            rollbackBoard(playerStone)
        }
    }

    private fun rollbackBoard(playerStone: Stone) {
        val row = playerStone.row.toIntIndex() - 1
        val column = playerStone.column.getIndex()
        OMockBoard.boardTable[row][column] = OMockBoard.boardDefaultTable[row][column]
    }
}
