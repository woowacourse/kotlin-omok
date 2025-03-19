package omok.controller

import omok.domain.OmokGame
import omok.domain.OmokResult
import omok.view.InputView
import omok.view.OutputView
import rule.wrapper.point.Point

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
        return OmokGame()
    }

    private fun printWinner(
        omokResult: OmokResult,
        omokGame: OmokGame,
    ) {
        outputView.printBoardState(omokGame.grid.board)
        outputView.printWinner(omokResult)
    }
}

fun Point.minus(value: Int): Point {
    return Point(this.row - value, this.col - value)
}
