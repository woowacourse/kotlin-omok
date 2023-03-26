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
            while (true) {
                val state = omokGame.placeStone(OutputView::printOmokBoard, InputView::requestPoint)
                if (state is OmokGameState.End) {
                    OutputView.printOmokBoard(omokGame.board)
                    OutputView.printWinner(state.getResult())
                    return
                }
            }
        }.onFailure { ex ->
            OutputView.printExceptionMessage(ex)
        }
    }
}
