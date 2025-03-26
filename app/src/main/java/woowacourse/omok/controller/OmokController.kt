package omok.controller

import omok.domain.game.OmokGame
import omok.event.OmokEventListener

class OmokController(
    private val game: OmokGame,
    private val eventListener: OmokEventListener,
) {
    fun startGame() {
        game.startGame(eventListener)
    }
}
