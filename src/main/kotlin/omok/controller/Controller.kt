package omok.controller

import omok.model.board.Board
import omok.model.game.GamePlayersEvent
import omok.model.game.OmokGame
import omok.model.game.state.BlackTurn
import omok.model.game.state.GameState
import omok.view.ConsoleOmokView
import omok.view.OmokInputView
import omok.view.OmokView

class Controller(
    private val inputView: OmokInputView,
    private val omokView: OmokView = ConsoleOmokView,
    private val state: GameState = BlackTurn(Board()),
    private val event: GamePlayersEvent =
        GamePlayersEvent(
            { inputView.readPosition().toPosition() },
            { inputView.readPosition().toPosition() },
        ),
) {
    fun start() {
        omokView.showStartMessage()
        val game =
            OmokGame(
                state = state,
                playersEvent = event,
            )
        val result = game.play(omokView::showProgress)

        omokView.showGameResult(result, requireNotNull(result.lastOrNull()) { "게임이 종료되지 않았습니다." })
    }
}
