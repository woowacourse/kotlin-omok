package omok.controller

import omok.domain.Board
import omok.domain.RenjuRuleAdapter
import omok.domain.Stone
import omok.domain.Turn
import omok.view.InputView
import omok.view.OutputView

class OmokGame(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun start() {
        val board = Board(RenjuRuleAdapter())
        val turn = Turn()
        outputView.printStartMessage()
        var prevPosition = ""
        while (true) {
            outputView.printBoard(board.grid)
            val lastStone: Stone? = board.stones.lastStone()
            messageTurn(lastStone, prevPosition)
            val inputPosition = inputView.readPosition()
            prevPosition = inputPosition
            val stone = prepareStone(turn, inputPosition)
            board.put(stone)
            if (board.isOmok(stone)) break
            turn.next()
        }
        outputView.showGameResult(turn)
    }

    private fun prepareStone(
        turn: Turn,
        inputPosition: String,
    ): Stone {
        return turn.stone(inputPosition)
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
