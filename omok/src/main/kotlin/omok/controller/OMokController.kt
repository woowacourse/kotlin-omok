package omok.controller

import omok.model.OMokGame
import omok.model.state.Stone
import omok.model.turn.FinishedTurn
import omok.model.turn.Turn
import omok.view.InputView.playerPick
import omok.view.OutputView
import omok.view.OutputView.outputBoard
import omok.view.OutputView.outputErrorMessage
import omok.view.OutputView.outputGameStart
import omok.view.OutputView.outputSuccessOMock
import omok.view.OutputView.outputUserTurn

class OMokController(private val oMokGame: OMokGame = OMokGame()) {
    fun run() {
        outputGameStart()

        while (oMokGame.getTurn() !is FinishedTurn) {
            oMokGame.playGame({ turn ->
                outputBoard()
                displayTurnInfo(turn)
                startPlayerTurn(turn)
            }) { e ->
                outputErrorMessage(e)
            }
        }

        outputBoard()
        outputSuccessOMock()
    }

    private fun displayTurnInfo(turn: Turn) {
        outputUserTurn(Stone.getStoneName(turn))
        turn.stoneHistory.lastOrNull()?.let { stone ->
            OutputView.outputLastStone(stone)
        } ?: OutputView.outputPrintLine()
    }

    private fun startPlayerTurn(turn: Turn): Stone? {
        return playerPick(turn).fold(
            onSuccess = { playerStone -> playerStone },
            onFailure = { error ->
                println(error)
                null
            },
        )
    }
}
