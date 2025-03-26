package omok.event

import omok.domain.board.OmokBoard
import omok.domain.place.Place
import omok.global.retryWhenNull
import omok.view.InputView
import omok.view.OutputView

class OmokEventListener(private val outputView: OutputView, private val inputView: InputView) : GameEventListner {
    override fun onFinished(winner: Place) {
        outputView.printPrintWinner(winner)
    }

    override fun onGameStart() {
        outputView.printStartMessage()
    }

    override fun onInputRequest(place: Place): String {
        return retryWhenNull {
            inputView.readStoneWithLastPosition(place)
        }
    }

    override fun onBoardView(omokBoard: OmokBoard) {
        outputView.printBoard(omokBoard)
    }
}
