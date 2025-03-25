package omok.event

import omok.domain.board.OmokBoard
import omok.domain.stone.Stone
import omok.global.retryWhenNull
import omok.view.InputView
import omok.view.OutputView

class OmokEventListener(private val outputView: OutputView, private val inputView: InputView) : GameEventListner {
    override fun onFinished(winner: Stone) {
        outputView.printPrintWinner(winner)
    }

    override fun onGameStart() {
        outputView.printStartMessage()
    }

    override fun onInputRequest(stone: Stone): String {
        return retryWhenNull {
            inputView.readStoneWithLastPosition(stone)
        }
    }

    override fun onBoardView(omokBoard: OmokBoard) {
        outputView.printBoard(omokBoard)
    }
}
