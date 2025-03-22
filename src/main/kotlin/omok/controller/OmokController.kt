package omok.controller

import omok.domain.OmokBoard
import omok.domain.Position
import omok.domain.turn.BlackTurn
import omok.domain.turn.Finished
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
            nowTurn = nowTurn.putStone(latestPosition, board)
        }
        outputView.printTurn(nowTurn)
    }

//    private fun printWinner(
//        omokResult: OmokResult,
//        omokGame: OmokGame,
//    ) {
//        outputView.printBoardState(omokGame.board)
//        outputView.printWinner(omokResult)
//    }
}
