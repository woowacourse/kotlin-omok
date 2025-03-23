package omok.controller

import omok.domain.Board
import omok.domain.Position
import omok.domain.RenjuRuleAdapter
import omok.domain.Stone
import omok.domain.Turn
import omok.view.InputView
import omok.view.OutputView

class OmokGame(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    var prevPosition = ""

    fun start() {
        val board = Board(RenjuRuleAdapter())
        val turn = Turn()
        outputView.printStartMessage()
        var checkBoardFull = false
        while (true) {
            outputView.printBoard(board.grid)
            val lastStone: Stone? = board.stones.lastStone()
            messageTurn(lastStone, prevPosition)
            val position = preparePosition()
            val stone = board.put(position, turn.color())
            if (board.isOmok(stone)) break
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
        val position = Position.from(inputPosition) ?: return preparePosition()
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
