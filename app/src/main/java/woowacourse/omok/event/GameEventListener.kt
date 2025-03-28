package omok.event

import omok.domain.board.OmokBoard
import omok.domain.place.Place

interface GameEventListener {
    fun onFinished(winner: Place?)

    fun onGameStart() {
    }

    fun onInvalidInput(message: String?)

    fun onInputRequest(place: Place): String {
        return ""
    }

    fun onBoardView(omokBoard: OmokBoard)
}
