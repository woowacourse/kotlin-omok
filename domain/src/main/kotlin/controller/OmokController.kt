package controller

import domain.OmokGame
import domain.OmokGameState
import domain.stone.Color
import view.InputView
import view.OutputView

class OmokController(private val omokGame: OmokGame = OmokGame()) {

    fun run() {
        runCatching {
            OutputView.printGameStartMessage()
            do{
                omokGame.placeStone(OutputView::printOmokBoard, InputView::requestPoint)
            }while(omokGame.state == OmokGameState.Running)
            OutputView.printOmokBoard(omokGame.board)
            OutputView.printWinner(omokGame.state.getResult())
        }.onFailure { ex ->
            OutputView.printExceptionMessage(ex)
        }
    }
}
