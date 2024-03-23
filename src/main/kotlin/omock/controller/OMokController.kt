package omock.controller

import omock.model.Board
import omock.model.state.Stone
import omock.model.turn.BlackTurn
import omock.model.turn.Turn
import omock.view.InputView.playerPick
import omock.view.OutputView.boardDefaultTable
import omock.view.OutputView.boardTable
import omock.view.OutputView.outputBoard
import omock.view.OutputView.outputGameStart
import omock.view.OutputView.outputLastStone
import omock.view.OutputView.outputPrintLine
import omock.view.OutputView.outputSuccessOMock
import omock.view.OutputView.outputUserTurn

class OMokController {
    private var turn: Turn = BlackTurn()
    private val board = Board.from()

    fun run() {
        outputGameStart()

        while (!turn.isFinished()) {
            outputBoard()
            processPlayerTurn()
        }
        outputBoard()
        outputSuccessOMock()
    }

    private fun processPlayerTurn() {
        displayTurnInfo()
        start()
    }

    private fun displayTurnInfo() {
        outputUserTurn(Stone.getStoneName(turn))
        turn.stoneHistory.lastOrNull()?.let { stone ->
            outputLastStone(stone)
        } ?: outputPrintLine()
    }

    private fun start() {
        playerPick(turn = turn).onSuccess { playerStone ->
            executePlayerTurn(playerStone)
        }.onFailure {
            println(it.message)
        }
    }

    private fun executePlayerTurn(playerStone: Stone) {
        playerTurn(playerStone).onSuccess {
            turn.stoneHistoryAdd(playerStone)
        }.onFailure { error ->
            handleTurnFailure(playerStone, error)
        }
    }

    private fun handleTurnFailure(
        playerStone: Stone,
        error: Throwable,
    ) {
        board.rollbackState(playerStone)
        rollbackBoard(playerStone)
        println(error.message)
    }

    private fun rollbackBoard(playerStone: Stone) {
        val row = playerStone.row.toIntIndex() - 1
        val column = playerStone.column.getIndex()
        boardTable[row][column] = boardDefaultTable[row][column]
    }

    private fun updateBoard(playerStone: Stone) {
        val row = playerStone.row.toIntIndex() - 1
        val column = playerStone.column.getIndex()
        boardTable[row][column] = Stone.getStoneIcon(turn)
    }

    private fun playerTurn(playerStone: Stone): Result<Unit> {
        return runCatching {
            board.setStoneState(turn, playerStone)
            updateBoard(playerStone)
            val visited = board.loadMap(playerStone)
            turn = turn.judgementResult(visited)
        }
    }
}
