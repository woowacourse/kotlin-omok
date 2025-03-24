package omok.controller

import omok.domain.Board
import omok.domain.Stone
import omok.domain.Turn
import omok.model.RenjuRule
import omok.view.InputView
import omok.view.OutputView

class OmokGame(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun start() {
        val board = Board(RenjuRule())
        val turn = Turn()
        outputView.printStartMessage()
        var position = ""
        while (true) {
            outputView.showBoard(board.grid)
            val lastStone: Stone? = board.stones.lastStone()
            if (lastStone == null) {
                outputView.printFirstTurn()
            } else {
                outputView.printNormalTurn(lastStone.color, position)
            }
            val inputPosition = inputView.readPosition()
            position = inputPosition
            val stone = getPosition(turn, inputPosition)
            board.put(stone)
            if (board.isOmok(stone)) break
            turn.next()
        }
        outputView.showGameResult(turn)
    }

    private fun getPosition(
        turn: Turn,
        inputPosition: String,
    ): Stone {
        return turn.stone(inputPosition)
    }
}
