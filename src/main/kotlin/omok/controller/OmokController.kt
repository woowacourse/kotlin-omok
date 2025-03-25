package omok.controller

import omok.controller.event.OmokGameHandler
import omok.domain.service.OmokGame
import omok.view.GameView

class OmokController(
    private val gameView: GameView,
    private val omokGame: OmokGame,
) {
    fun run() {
        gameView.printStartMessage()
        omokGame.startGame(OmokGameHandler(gameView, omokGame))
    }
}
