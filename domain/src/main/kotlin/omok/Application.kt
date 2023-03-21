package omok

import omok.controller.OmokController
import omok.domain.OmokBoard
import omok.domain.OmokGameListener
import omok.domain.OmokPoint
import omok.domain.state.StoneState
import omok.view.ErrorView
import omok.view.InputView
import omok.view.OutputView

fun main() {
    OmokController(
        object : OmokGameListener {
            override fun onOmokStart() {
                OutputView.outputInit()
            }

            override fun onPointRequest(stoneState: StoneState, point: OmokPoint?): OmokPoint {
                return InputView.inputPoint(stoneState, point)
            }

            override fun onBoardShow(omokBoard: OmokBoard) {
                OutputView.outputBoard(omokBoard)
            }

            override fun onError(message: String?) {
                ErrorView.error(message)
            }
        },
    ).run()
}
