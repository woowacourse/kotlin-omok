package omok.controller

import omok.domain.OmokAdapter
import omok.domain.OmokBoard
import omok.domain.OmokGame
import omok.domain.Position
import omok.domain.StoneState
import omok.domain.turn.PutStoneResult
import omok.domain.turn.PutStoneResult.Success.Finished
import omok.domain.turn.PutStoneResult.Success.NextTurn
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
            outputView.printBoardState(omokGame.board)
            outputView.printTurn(nowTurn)
            latestPosition = inputView.getPosition(latestPosition)
            when (val putResult = omokGame.putStone(latestPosition)) {
                is PutStoneResult.Success<*> -> {
                    when (val result = putResult.result) {
                        is NextTurn -> {
                            nowTurn = result.turn
                            continue
                        }

                        is Finished -> {
                            outputView.printBoardState(omokGame.board)
                            outputView.printWinner(result.turn)
                            return
                        }
                    }
                }

                is PutStoneResult.Failure -> outputView.printError(putResult.message)
            }
        }
    }
}
