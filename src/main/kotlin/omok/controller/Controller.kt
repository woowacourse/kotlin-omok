package omok.controller

import omok.model.Board
import omok.model.OmokGame
import omok.model.event.PutEvent
import omok.model.rule.BlackPutRule
import omok.model.state.GameState
import omok.view.ConsoleOmokView
import omok.view.OmokView

class Controller(
    private val omokView: OmokView = ConsoleOmokView,
    private val state: GameState = GameState.Running.BlackTurn(BlackPutRule, Board(stones = emptyMap())),
    private val event: PutEvent =
        PutEvent(
            omokView::readPosition,
            omokView::readPosition,
        ),
) {
    fun start() {
        omokView.showStartMessage()
        val game =
            OmokGame(
                state = state,
                putEvent = event,
                onFinishGame = omokView::showGameResult,
            )
        game.play(omokView::showProgress)
    }
}
