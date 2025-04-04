package woowacourse.omok.controller

import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import omok.view.OutputView
import rule.BlackRenjuRule
import rule.wrapper.point.Point
import woowacourse.omok.controller.GameState.Playing
import woowacourse.omok.controller.GameState.Win
import woowacourse.omok.mapper.BlackRuleChecker
import woowacourse.omok.model.game.Game
import woowacourse.omok.model.game.PlayResult
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
        var state: GameState = Playing

        do {
            state = transition(state)
        } while (state !is Win)
        transition(state)
    }

    fun transition(current: GameState): GameState {
        return when (current) {
            is Playing -> {
                printCurrentState()
                val position = readPosition() ?: return current

                handlePlayResult(position)
            }

            is Win -> {
                outputView.printBoard(game.board)
                outputView.printOmok(game.lastStone)
                return current
            }
        }
    }

    private fun handlePlayResult(position: Position): GameState =
        when (val result = game.playTurn(position)) {
            is PlayResult.Success -> Playing
            is PlayResult.Violation -> handleViolation(result.error)
            is PlayResult.Win -> Win(result.winner)
        }

    private fun handleViolation(error: PlacementError): GameState {
        outputView.printException(error)
        return Playing
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

    private fun printCurrentState() {
        outputView.printBoard(game.board)
        outputView.printNextTurn(game.turn, game.lastStone)
    }
}
