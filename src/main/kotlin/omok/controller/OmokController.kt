package omok.controller

import omok.domain.OmokBoard
import omok.domain.Position
import omok.domain.turn.BlackTurn
import omok.domain.turn.Finished
import omok.domain.turn.PutStoneResult
import omok.domain.turn.Turn
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    private val board = OmokBoard()

    fun start() {
        outputView.printStartMessage()
        playGame()
    }

    private fun playGame() {
        var latestPosition: Position? = null
        var nowTurn: Turn = BlackTurn()
        while (nowTurn !is Finished) {
            outputView.printBoardState(board)
            outputView.printTurn(nowTurn)
            latestPosition = inputView.getPosition(latestPosition)
            when (val result = nowTurn.putStone(latestPosition, board)) {
                is PutStoneResult.Success -> nowTurn = result.turn
                is PutStoneResult.Failure -> outputView.printError(result.message)
            }
        }
        outputView.printBoardState(board)
        outputView.printTurn(nowTurn)
    }
}
