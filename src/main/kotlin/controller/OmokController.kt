package controller

import domain.OmokGame
import domain.judgement.FiveStoneWinningCondition
import domain.judgement.RenjuRuleForbiddenCondition
import view.InputView
import view.OutputView

class OmokController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView()
) : Runnable {
    override fun run() {
        outputView.printGameStartMessage()
        val omokGame = OmokGame(inputView::requestPoint, outputView::printOmokBoardState)
        val result = omokGame.playOmokGameAndReturnWinner(
            FiveStoneWinningCondition(),
            RenjuRuleForbiddenCondition()
        )
        outputView.printWinner(result)
    }
}
