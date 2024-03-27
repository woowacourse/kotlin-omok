package omok.controller

import omok.model.OmokStone
import omok.model.board.Board
import omok.model.game.GamePlayersEvent
import omok.model.game.OmokGame
import omok.model.game.state.BlackTurn
import omok.model.game.state.GameState
import omok.view.OmokInputView
import omok.view.OmokOutputView

class Controller(
    private val inputView: OmokInputView,
    private val outputView: OmokOutputView,
    private val state: GameState = BlackTurn(Board()),
    private val event: GamePlayersEvent =
        GamePlayersEvent(
            { inputView.readPosition().toPosition() },
            { inputView.readPosition().toPosition() },
        ),
) {
    fun start() {
        outputView.showStartMessage()
        val game =
            OmokGame(
                state = state,
                playersEvent = event,
            )
        val (board, winner) = game.play(::showCurrentGameState)
        outputView.showGameResult(board.toUiModel(), winner.toUiModel())
    }

    private fun showCurrentGameState(
        board: Board,
        stone: OmokStone?,
    ) {
        outputView.showCurrentGameState(board.toUiModel(), stone?.toUiModel())
    }
}
