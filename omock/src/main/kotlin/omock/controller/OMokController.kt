package omock.controller

import omock.model.OMockGame
import omock.model.state.Stone
import omock.model.turn.FinishedTurn
import omock.model.turn.Turn
import omock.view.InputView.playerPick
import omock.view.OutputView
import omock.view.OutputView.outputBoard
import omock.view.OutputView.outputErrorMessage
import omock.view.OutputView.outputGameStart
import omock.view.OutputView.outputSuccessOMock
import omock.view.OutputView.outputUserTurn

class OMokController(private val oMockGame: OMockGame = OMockGame()) {
    fun run() {
        outputGameStart()

        while (oMockGame.getTurn() !is FinishedTurn) {
            oMockGame.playGame({ turn ->
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
