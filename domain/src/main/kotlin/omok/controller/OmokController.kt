package omok.controller

import omok.OmokGame
import omok.OmokPoint
import omok.view.InputView
import omok.view.OutputView

class OmokController(private val inputView: InputView, private val outputView: OutputView) {
    private val omokGame = OmokGame()

    fun run() {
        outputView.outputInit()
        startGame()
    }

    private tailrec fun startGame(point: OmokPoint? = null) {
        var tempPoint = inputView.inputPoint(omokGame.gameState.stoneState, point)
        runCatching {
            omokGame.play(tempPoint)
            outputView.outputBoard(omokGame.gameState.omokBoard)
        }
            .onFailure {
                outputView.outputError(it.message)
                tempPoint = point!!
            }
        if (omokGame.gameState.isRunning) startGame(tempPoint)
    }
}
