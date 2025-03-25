package omok.controller

import omok.model.Board
import omok.model.adapter.RenjuRuleAdapter
import omok.model.game.FoulConditionResult
import omok.model.game.GameState
import omok.model.game.InvalidMoveResult
import omok.model.stone.Point
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.view.InputView
import omok.view.OutputView

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
            val foulConditionResult: FoulConditionResult? = board.checkFoulCondition(stone)
            val invalidMoveResult: InvalidMoveResult? = board.checkInvalidMove(stone)
            if (shouldRetry(board.checkFoulCondition(stone), board.checkInvalidMove(stone))) {
                continue
            }
            if (invalidMoveResult is InvalidMoveResult.FullBoard) {
                break
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

    private fun shouldRetry(
        foulConditionResult: FoulConditionResult?,
        invalidMoveResult: InvalidMoveResult?,
    ): Boolean {
        if (foulConditionResult != null) {
            return true
        }
        if (invalidMoveResult != null && invalidMoveResult !is InvalidMoveResult.FullBoard) {
            return true
        }
        return false
    }
}
