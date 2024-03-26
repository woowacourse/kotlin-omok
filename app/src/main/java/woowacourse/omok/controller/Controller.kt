package omok.controller

import omok.model.Board
import omok.model.OmokGame
import omok.model.event.PutEvent
import omok.model.rule.RenjuRule
import omok.model.state.BlackTurn
import omok.model.state.GameState
import omok.view.ConsoleOmokInputView
import omok.view.ConsoleOmokOutputView
import omok.view.OmokOutputView

class Controller(
    private val omokOutputView: OmokOutputView = ConsoleOmokOutputView,
    private val state: GameState = BlackTurn(RenjuRule, Board(emptyMap())),
    private val event: PutEvent =
        PutEvent(
            { ConsoleOmokInputView.readPosition() },
            { ConsoleOmokInputView.readPosition() },
        ),
) {
    fun start() {
        omokOutputView.showStartMessage()
        val game =
            OmokGame(
                state = state,
                putEvent = event,
                onFinishGame = omokOutputView::showGameResult,
            )
        game.play(omokOutputView::showProgress)
    }
}
