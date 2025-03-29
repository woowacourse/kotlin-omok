package woowacourse.omok.controller

import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import omok.view.OutputView
import rule.BlackRenjuRule
import rule.wrapper.point.Point
import woowacourse.omok.mapper.BlackRuleChecker
import woowacourse.omok.model.game.Game
import woowacourse.omok.model.rule.CoordinateResult
import woowacourse.omok.model.rule.PlacementError
import woowacourse.omok.view.InputView

class OmokControl(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    private val game =
        Game(
            BlackRuleChecker(
                rule = BlackRenjuRule(),
                mapper = { position -> Point(position.col.value + 1, position.row.value + 1) },
            ),
        )

    fun run() {
        play()
    }

    private fun play(showBoard: Boolean = true) {
        if (showBoard) printCurrentState()

        val position = readPosition() ?: return play(showBoard = false)
        val violation = processPlacement(position)

        if (violation != PlacementError.NoViolation) {
            handleViolation(violation)
            return
        }

        if (checkGameEnd()) return

        play()
    }

    private fun readPosition(): Position? =
        when (val result = inputView.inputStone(game.board)) {
            is CoordinateResult.Success -> {
                Position(Row(result.row), Col(result.col))
            }

            is CoordinateResult.Failure -> {
                outputView.printCoordinateException(result.reason)
                null
            }
        }

    private fun processPlacement(position: Position): PlacementError = game.playTurn(position)

    private fun checkGameEnd(): Boolean {
        if (game.isOmok()) {
            outputView.printBoard(game.board)
            outputView.printOmok(game.lastStone)
            return true
        }
        return false
    }

    private fun printCurrentState() {
        outputView.printBoard(game.board)
        outputView.printNextTurn(game.turn, game.lastStone)
    }

    private fun handleViolation(violation: PlacementError) {
        outputView.printException(violation)
        play(showBoard = false)
    }
}
