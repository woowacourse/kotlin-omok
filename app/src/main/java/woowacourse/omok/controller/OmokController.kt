package woowacourse.omok.controller

import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.Game
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.Stone
import woowacourse.omok.model.position.Position
import woowacourse.omok.model.rule.RenjuRule
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        outputView.printOmokStart()
        val game = Game(Board(), RenjuRule())
        outputView.printBoard(game.board)
        retryOnError { processTurn(game) }
    }

    private tailrec fun processTurn(game: Game) {
        val position: Position = inputView.readTurn(game)
        val color: Color = game.chooseTurn()
        val moveResult: MoveResult = game.play(Stone(position, color))
        outputView.printBoard(game.board)

        when (moveResult) {
            is MoveResult.Success.Playing -> processTurn(game)
            is MoveResult.Success.Finished -> {
                outputView.printMoveResult(game, moveResult)
                return
            }
            is MoveResult.Failure -> {
                outputView.printMoveResult(game, moveResult)
                processTurn(game)
            }
        }
    }

    private fun <T> retryOnError(function: () -> T): T {
        return runCatching { function() }.getOrElse { error ->
            println(error.message)
            retryOnError(function)
        }
    }
}
