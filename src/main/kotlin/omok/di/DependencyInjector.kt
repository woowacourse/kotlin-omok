package omok.di

import omok.controller.OmokController
import omok.domain.board.OmokBoard
import omok.domain.point.OmokPoints
import omok.domain.service.OmokGame
import omok.view.ConsoleGameView
import omok.view.InputView
import omok.view.OutputView

class DependencyInjector {
    fun injectController(): OmokController {
        val inputView = injectInputView()
        val outputView = injectOutputView()
        val gameView = injectGameView(outputView, inputView)
        val omokBoard = injectOmokBoard()
        val omokGame = injectOmokGame(omokBoard)
        return OmokController(gameView, omokGame)
    }

    private fun injectInputView(): InputView = InputView()

    private fun injectOutputView(): OutputView = OutputView()

    private fun injectGameView(
        outputView: OutputView,
        inputView: InputView,
    ): ConsoleGameView {
        return ConsoleGameView(outputView, inputView)
    }

    private fun injectOmokPoints(): OmokPoints = OmokPoints()

    private fun injectOmokBoard(): OmokBoard {
        val points = injectOmokPoints()
        return OmokBoard(points)
    }

    private fun injectOmokGame(omokBoard: OmokBoard): OmokGame = OmokGame(omokBoard)
}
