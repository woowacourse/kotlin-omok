package omok.model

import omok.model.Column.Companion.toColumnComma
import omok.model.ErrorType.AlreadyExistStone
import omok.model.Row.Companion.toRowComma
import omok.model.state.Stone
import omok.model.turn.BlackTurn
import omok.model.turn.Turn

class OMokGame(
    private var turn: Turn = BlackTurn(),
    private val board: Board = Board.from(),
) {
    fun getTurn() = turn

    fun playGame(
        showBoard: (Turn) -> Stone?,
        error: (Throwable) -> Unit,
    ) {
        if (!turn.isFinished()) {
            showBoard(turn)?.let { playerStone ->
                executePlayerTurn(playerStone, error)
            }
        }
    }

    fun executeValidCoordinates(
        idx: Int,
        size: Int,
        listener: ValidCoordinatesListener,
    ) {
        val rowIndex = idx / size
        val columnIndex = idx % size

        val rowComma = rowIndex.toRowComma(size)
        val columnComma = columnIndex.toColumnComma()

        if (executeTurn(Row(rowComma), Column(columnComma))) {
            listener.onValidCoordinates(rowComma, columnComma)
        } else {
            listener.onInvalidCoordinates()
        }
    }

    fun executeTurn(
        row: Row,
        column: Column,
    ): Boolean {
        var isSuccess = true

        playGame({ turn ->
            Stone(row, column)
        }) { e ->
            isSuccess = false
        }
        return isSuccess
    }

    private fun executePlayerTurn(
        playerStone: Stone,
        error: (Throwable) -> Unit,
    ) {
        playerTurn(playerStone).onSuccess { newTurn ->
            turn = newTurn
            turn.stoneHistoryAdd(playerStone)
        }.onFailure { e ->
            handleTurnFailure(playerStone, e)
            error(e)
        }
    }

    private fun playerTurn(playerStone: Stone): Result<Turn> {
        board.setStoneState(turn, playerStone).onSuccess {
            updateBoard(playerStone)
            val row = playerStone.row.getIndex()
            val column = playerStone.column.getIndex()
            return turn.processTurn(board.stoneStates, row, column)
        }.onFailure { e ->
            return Result.failure(e)
        }
        return Result.failure(AlreadyExistStone())
    }

    private fun updateBoard(playerStone: Stone) {
        val row = playerStone.row.toIntIndex() - 1
        val column = playerStone.column.getIndex()
        OMokBoard.boardTable[row][column] = Stone.getStoneIcon(turn)
    }

    private fun handleTurnFailure(
        playerStone: Stone,
        error: Throwable,
    ) {
        if (error !is AlreadyExistStone) {
            board.rollbackState(playerStone)
            rollbackBoard(playerStone)
        }
    }

    private fun rollbackBoard(playerStone: Stone) {
        val row = playerStone.row.toIntIndex() - 1
        val column = playerStone.column.getIndex()
        OMokBoard.boardTable[row][column] = OMokBoard.boardDefaultTable[row][column]
    }
}
