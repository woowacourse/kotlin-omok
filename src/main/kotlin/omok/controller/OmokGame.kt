package omok.controller

import omok.domain.Board
import omok.domain.Stone
import omok.view.InputView
import omok.view.OutputView
import omok.domain.Turn
import omok.model.RenjuRuleAdapter

class OmokGame(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun start() {
        val board = Board(RenjuRuleAdapter())
        val turn = Turn()
        outputView.printStartMessage()
        var inputPositison1 = ""
        while (true) {
            outputView.showBoard(board.grid)
            val lastStone: Stone? = board.stones.lastStone()
            if (lastStone == null ) {
                outputView.printFirstTurn()
            } else {
                outputView.printNormalTurn(lastStone.color, inputPositison1)
            }
            val inputPosition = inputView.readPosition()
            inputPositison1 = inputPosition
            val stone = getPosition(turn, inputPosition)
            board.put(stone)
            if (board.isOmok(stone)) break
            turn.next()
        }
        outputView.showGameResult(turn)
    }

    private fun getPosition(turn: Turn, inputPosition: String): Stone {
        return turn.stone(inputPosition)
    }
}