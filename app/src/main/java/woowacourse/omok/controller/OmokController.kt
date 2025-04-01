package woowacourse.omok.controller

import woowacourse.omok.model.Board
import woowacourse.omok.model.adapter.RenjuRuleAdapter
import woowacourse.omok.model.game.GameState
import woowacourse.omok.model.game.ViolationResult
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val board = Board(renjuRuleAdapter = RenjuRuleAdapter())
        outputView.printOmokStart()
        outputView.printBoard(board)
        playOmok(board)
    }

    private fun playOmok(board: Board) {
        while (true) {
            val stone = board.currentStone(inputView.readTurn(board.stones.lastStone))
            when (val violationResult: ViolationResult = board.checkViolation(stone)) {
                is ViolationResult.Success -> Unit
                is ViolationResult.Failure.InvalidMoveResult.FullBoard -> {
                    outputView.printErrorMessage(violationResult.message)
                    break
                }

                is ViolationResult.Failure -> {
                    outputView.printErrorMessage(violationResult.message)
                    continue
                }
            }

            board.place(stone)
            val gameState = board.gameState(stone)
            outputView.printBoard(board)
            if (gameState != GameState.PLAYING) {
                outputView.printWinner(gameState)
                break
            }
        }
    }
}
