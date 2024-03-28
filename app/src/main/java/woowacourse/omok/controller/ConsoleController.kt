package woowacourse.omok.controller

import woowacourse.omok.model.Board
import woowacourse.omok.model.Position
import woowacourse.omok.model.rule.RenjuRule
import woowacourse.omok.model.state.BlackTurn
import woowacourse.omok.model.state.GameState
import woowacourse.omok.view.ConsoleOmokInputView
import woowacourse.omok.view.ConsoleOmokOutputView
import woowacourse.omok.view.OmokInputView
import woowacourse.omok.view.OmokView

class ConsoleController(
    private val outputView: ConsoleOmokOutputView = ConsoleOmokOutputView,
    private val inputView: OmokInputView = ConsoleOmokInputView,
    private var gameState: GameState = BlackTurn(RenjuRule, Board(emptyMap())),
) : OmokController {
    fun start() {
        outputView.showStartMessage()
        while (gameState !is GameState.Finish) {
            play()
        }
    }

    private fun play() {
        val position = inputView.readPosition()
        onPlace(outputView, position)
    }

    override fun onPlace(
        view: OmokView,
        position: Position,
    ) {
        gameState = gameState.put(position)
        view.updateBoard(position, gameState.board)
    }
}
