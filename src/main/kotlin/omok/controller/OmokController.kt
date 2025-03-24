package omok.controller

import omok.domain.OmokAdapter
import omok.domain.OmokBoard
import omok.domain.OmokGame
import omok.domain.Position
import omok.domain.StoneState
import omok.domain.turn.PutStoneResult
import omok.domain.turn.PutStoneResult.Failure
import omok.domain.turn.PutStoneResult.NextTurn
import omok.domain.turn.TurnManager
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    private val board = OmokBoard(rule = OmokAdapter())
    private val omokGame = OmokGame(board, TurnManager())

    fun start() {
        outputView.printStartMessage()
        playGame()
    }

    private fun playGame() {
        var latestPosition: Position? = null
        var nowTurn: StoneState = StoneState.BLACK
        while (true) {
            printGameStatus(nowTurn)
            latestPosition = inputView.getPosition(latestPosition)
            when (val putResult = omokGame.putStone(latestPosition)) {
                is NextTurn -> {
                    nowTurn = putResult.turn
                    continue
                }

                is PutStoneResult.Finished -> {
                    outputView.printBoardState(omokGame.board)
                    outputView.printWinner(putResult.turn)
                    return
                }

                is Failure -> outputView.printError(putResult.message)
            }
        }
    }

    private fun printGameStatus(nowTurn: StoneState) {
        outputView.printBoardState(omokGame.board)
        outputView.printTurn(nowTurn)
    }
}
