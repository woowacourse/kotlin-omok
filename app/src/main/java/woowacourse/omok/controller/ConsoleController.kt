package woowacourse.omok.controller

import woowacourse.omok.model.event.OnPlaceListener
import woowacourse.omok.model.game.OmokGame
import woowacourse.omok.model.omok.Board
import woowacourse.omok.model.rule.RenjuRule
import woowacourse.omok.model.state.BlackTurn
import woowacourse.omok.model.state.GameState
import woowacourse.omok.view.ConsoleOmokInputView
import woowacourse.omok.view.ConsoleOmokOutputView
import woowacourse.omok.view.OmokOutputView

class ConsoleController(
    private val omokOutputView: OmokOutputView = ConsoleOmokOutputView,
    private val state: GameState = BlackTurn(RenjuRule, Board()),
    private val event: OnPlaceListener = OnPlaceListener { ConsoleOmokInputView.readPosition() },
) {
    fun start() {
        omokOutputView.showStartMessage()
        val game =
            OmokGame(
                state = state,
                placeListener = event,
                onFinishGame = omokOutputView::showGameResult,
            )
        game.play(omokOutputView::showProgressWithBoard)
    }
}
