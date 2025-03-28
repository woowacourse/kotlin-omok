package omok.event

import omok.domain.board.OmokBoard
import omok.domain.place.Place

interface GameEventListner {
    fun onFinished(winner: Place)

    fun onGameStart()

    fun onInputRequest(place: Place): String

    fun onBoardView(omokBoard: OmokBoard)
}
