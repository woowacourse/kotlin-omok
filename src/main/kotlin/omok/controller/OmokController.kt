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
                onTurnStarted = { outputView.printBoardState(omokGame.grid) },
                onSelectPosition = { player, latestPoint, grid -> inputView.getPoint(player, latestPoint, grid) },
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
        outputView.printBoardState(omokGame.grid)
        outputView.printWinner(omokResult)
    }
}
