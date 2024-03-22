package omock.controller

import omock.model.Board
import omock.model.Stone
import omock.model.turn.BlackTurn
import omock.model.turn.Turn
import omock.view.InputView.playerPick
import omock.view.OutputView.boardTable
import omock.view.OutputView.outputBoard
import omock.view.OutputView.outputGameStart
import omock.view.OutputView.outputLastStone
import omock.view.OutputView.outputPrintLine
import omock.view.OutputView.outputSuccessOMock
import omock.view.OutputView.outputUserTurn

class OMokController {
    private var player: Turn = BlackTurn()
    private val board = Board.from()

    fun run() {
        outputGameStart()

        while (!player.isFinished()) {
            outputBoard()
            processPlayerTurn()
        }

        outputSuccessOMock()
    }

    private fun processPlayerTurn() {
        displayTurnInfo()
        start()
    }

    private fun displayTurnInfo() {
        outputUserTurn(Stone.getStoneName(player))
        player.stoneHistory.lastOrNull()?.let { stone ->
            outputLastStone(stone)
        } ?: outputPrintLine()
    }

    private fun start() {
        playerPick(player = player).onSuccess { playerStone ->
            executePlayerTurn(playerStone)
        }.onFailure {
            println(it.message)
        }
    }

    private fun executePlayerTurn(playerStone: Stone) {
        playerTurn(playerStone).onSuccess {
            updateBoard(playerStone)
            player.stoneHistoryAdd(playerStone)
        }.onFailure { error ->
            handleTurnFailure(playerStone, error)
        }
    }

    private fun handleTurnFailure(
        playerStone: Stone,
        error: Throwable,
    ) {
        board.rollbackState(playerStone)
        println(error.message)
    }

    private fun updateBoard(playerStone: Stone) {
        val row = playerStone.row.toIntIndex() - 1
        val column = playerStone.column.getIndex()
        boardTable[row][column] = Stone.getStoneIcon(player)
    }

    private fun playerTurn(playerStone: Stone): Result<Unit> {
        return runCatching {
            board.setStoneState(player, playerStone)
            val visited = board.loadMap(playerStone)
            player = player.judgementResult(visited)
        }
    }
}
