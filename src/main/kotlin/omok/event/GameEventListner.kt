package omok.event

import omok.domain.board.OmokBoard
import omok.domain.stone.Stone

interface GameEventListner {
    fun onFinished(winner: Stone)

    fun onGameStart()

    fun onInputRequest(stone: Stone): String

    fun onBoardView(omokBoard: OmokBoard)
}
