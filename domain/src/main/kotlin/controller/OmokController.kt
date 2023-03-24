package controller

import domain.OmokGame
import domain.stone.Color
import domain.stone.Position
import view.InputView
import view.OutputView

class OmokController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView()
) : Runnable {
    override fun run() {
        outputView.printGameStartMessage()
        val omokGame = OmokGame()
        while (omokGame.isFinished) {
            outputView.printOmokBoardState(omokGame.board)
            tryTurn(omokGame, omokGame.turnColor, omokGame.latestStone?.position)
        }
        outputView.printOmokBoardState(omokGame.board)
        outputView.printWinner(omokGame.winnerColor!!)
    }

    private fun tryTurn(
        omokGame: OmokGame,
        turnColor: Color = Color.BLACK,
        latestPosition: Position? = null
    ) {
        val position = inputView.requestPoint(turnColor, latestPosition)
        val playTurnIsSuccess = omokGame.playTurn(position)
        if (playTurnIsSuccess == null && omokGame.isFinished.not()) {
            tryTurn(omokGame, turnColor, latestPosition)
        }
    }
}
