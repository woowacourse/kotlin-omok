package omok.controller

import omok.domain.OmokGame
import omok.domain.OmokGrid
import omok.domain.OmokResult
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun play() {
        val omokGame = initGame()
        val result =
            omokGame.playGame(
                onTurnStarted = { outputView.printBoardState(it) },
                onSelectPosition = { player, latestPoint -> inputView.getPoint(player, latestPoint) },
            )
        printWinner(result, omokGame)
    }

    private fun initGame(): OmokGame {
        outputView.printStartMessage()
        return OmokGame(OmokGrid())
    }

    private fun printWinner(
        omokResult: OmokResult,
        omokGame: OmokGame,
    ) {
        outputView.printBoardState(omokGame.grid.board)
        outputView.printWinner(omokResult)
    }
}
