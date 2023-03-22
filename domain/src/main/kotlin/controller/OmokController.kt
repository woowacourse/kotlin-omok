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
        while (omokGame.turn.boardState.isFinished()) {
            outputView.printOmokBoardState(omokGame.turn.boardState.board)
            tryTurn(omokGame, omokGame.turn.color, omokGame.turn.boardState.latestStone?.position)
        }
        outputView.printOmokBoardState(omokGame.turn.boardState.board)
        outputView.printWinner(omokGame.turn.winnerColor!!)
    }

    private fun tryTurn(
        omokGame: OmokGame,
        turnColor: Color = Color.BLACK,
        latestPosition: Position? = null
    ) {
        val position = inputView.requestPoint(turnColor, latestPosition)
        val playTurnIsSuccess = omokGame.playTurn(position)
        if (playTurnIsSuccess == null && omokGame.turn.boardState.isFinished().not()) {
            tryTurn(omokGame, turnColor, latestPosition)
        }
    }
}
