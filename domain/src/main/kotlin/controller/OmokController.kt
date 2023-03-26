package controller

import domain.OmokGame
import view.InputView
import view.OutputView

class OmokController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView()
) : Runnable {
    override fun run() {
        outputView.printGameStartMessage()
        val omokGame = OmokGame()
        outputView.printOmokBoardState(omokGame.board)
        while (omokGame.isFinished.not()) {
            val position =
                inputView.requestPoint(omokGame.turnColor, omokGame.latestStone?.position)
            omokGame.playTurn(position, ::successProcess, ::failedProcess)
        }
    }

    private fun successProcess(omokGame: OmokGame) {
        outputView.printOmokBoardState(omokGame.board)
        if (omokGame.isFinished) {
            outputView.printWinner(omokGame.winnerColor!!)
        }
    }

    private fun failedProcess() {
        outputView.printFailedPutStone()
    }
}
