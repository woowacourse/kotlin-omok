package omok.controller

import omok.domain.OmokBoard
import omok.domain.OmokGame
import omok.domain.OmokResult
import omok.domain.Position
import omok.domain.StoneState
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
        var nowTurn: StoneState = StoneState.BLACK
        while (true) {
            outputView.printBoardState(board)
            latestPosition = inputView.getPosition(latestPosition)
            playTurn(latestPosition, nowTurn)
            nowTurn = if (nowTurn == StoneState.BLACK) StoneState.WHITE else StoneState.BLACK
        }
    }

    private fun playTurn(
        position: Position,
        nowTurn: StoneState,
    ) {
        board.putStone(position, nowTurn)
    }

    private fun printWinner(
        omokResult: OmokResult,
        omokGame: OmokGame,
    ) {
        outputView.printBoardState(omokGame.board)
        outputView.printWinner(omokResult)
    }
}
