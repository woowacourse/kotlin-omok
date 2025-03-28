package omok.event

import omok.domain.board.OmokBoard
import omok.domain.place.Place
import woowacourse.omok.view.OmokView

class OmokEventListener(private val view: OmokView) : GameEventListener {
    override fun onFinished(winner: Place?) {
        view.printInfoWhenFinished(winner)
    }

    override fun onInvalidInput(message: String?) {
        message?.let {
            view.printInvalidInput(it)
        }
    }

    override fun onBoardView(omokBoard: OmokBoard) {
        view.printBoard(omokBoard)
    }
}
