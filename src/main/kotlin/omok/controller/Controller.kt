package omok.controller

import omok.model.board.Board
import omok.model.game.OmokGame
import omok.model.game.OmokPlayersPlaceEvent
import omok.model.state.GameState
import omok.view.ConsoleOmokView
import omok.view.OmokView

class Controller(
    private val omokView: OmokView = ConsoleOmokView,
    private val state: GameState = GameState.Running.BlackTurn(Board()),
    private val event: OmokPlayersPlaceEvent =
        OmokPlayersPlaceEvent(
            omokView::readPosition,
            omokView::readPosition,
        ),
) {
    fun start() {
        omokView.showStartMessage()
        val game =
            OmokGame(
                state = state,
                playersEvent = event,
                onFinishGame = omokView::showGameResult,
            )
        game.play(omokView::showProgress)
    }
}
