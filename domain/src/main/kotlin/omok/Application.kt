package omok

import omok.controller.OmokController
import omok.domain.OmokBoard
import omok.domain.OmokGameListener
import omok.domain.OmokPoint
import omok.view.ErrorView
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val omokController = OmokController(
        object : OmokGameListener {
            override fun onOmokStart() {
                OutputView.outputInit()
            }

            override fun onBoardShow(omokBoard: OmokBoard, omokPoint: OmokPoint?) {
                OutputView.outputBoard(omokBoard)
            }

            override fun onError(message: String?) {
                ErrorView.error(message)
            }
        },
    )

    var point: OmokPoint? = null
    while (omokController.gameState.isRunning) {
        point = InputView.inputPoint(omokController.gameState.stoneState, point)
        omokController.run(point)
    }
}
