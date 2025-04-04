package woowacourse.omok.console.controller

import omok.domain.Turn
import woowacourse.omok.console.view.InputView
import woowacourse.omok.console.view.OutputView
import woowacourse.omok.domain.Board
import woowacourse.omok.domain.FiveRule
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.RenjuRuleAdapter
import woowacourse.omok.domain.Stone

class OmokGame(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    private var prevPosition = ""

    fun start() {
        val board = Board(RenjuRuleAdapter())
        val turn = Turn()
        val fiveRule = FiveRule()
        outputView.printStartMessage()
        var checkBoardFull = false
        while (true) {
            outputView.printBoard(board.grid)
            val lastStone: Stone? = board.stones.lastStone()
            messageTurn(lastStone, prevPosition)
            val position = preparePosition()
            val stone = board.put(position, turn.color)
            if (fiveRule.isOmok(stone, board.stones)) break
            turn.next()
            if (board.isFull()) {
                checkBoardFull = true
                break
            }
        }
        if (checkBoardFull) {
            outputView.showGameDrawResult()
        } else {
            outputView.showGameResult(turn)
        }
    }

    private fun preparePosition(): Position {
        val inputPosition = inputView.readPosition()
        val position = Position(inputPosition)
        prevPosition = inputPosition
        return position
    }

    private fun messageTurn(
        lastStone: Stone?,
        position: String,
    ) {
        if (lastStone == null) {
            outputView.printFirstTurn()
        } else {
            outputView.printNormalTurn(lastStone.color, position)
        }
    }
}
