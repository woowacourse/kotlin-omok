package woowacourse.omok.controller

import woowacourse.omok.model.Board
import woowacourse.omok.model.adapter.RenjuRuleAdapter
import woowacourse.omok.model.game.GameState
import woowacourse.omok.model.game.ViolationResult
import woowacourse.omok.model.stone.Point
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
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
            val stone = setStone(board)
            when (val violationResult: ViolationResult? = board.checkViolation(stone)) {
                null -> Unit
                is ViolationResult.InvalidMoveResult.FullBoard -> {
                    outputView.printErrorMessage(violationResult.message)
                    break
                }
                else -> {
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

    private fun setStone(board: Board): Stone {
        val lastStone = board.stones.lastStone
        val inputPoint: Point = inputView.readTurn(lastStone)
        val nextColor: StoneColor = (lastStone?.color ?: StoneColor.WHITE).reverse()
        return Stone(inputPoint, nextColor)
    }
}
