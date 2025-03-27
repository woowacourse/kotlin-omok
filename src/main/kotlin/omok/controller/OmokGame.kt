package omok.controller

import omok.domain.Board
import omok.domain.RenjuRule
import omok.domain.Stone
import omok.domain.Turn
import omok.view.InputView
import omok.view.OutputView

class OmokGame(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun start() {
        val board = Board(rule = RenjuRule())
        val turn = Turn()
        outputView.printStartMessage()
        var position = Position(0, 0)
        while (true) {
            outputView.showBoard(board.grid)
            val lastStone: Position? = board.lastMove
            if (lastStone == null) {
                outputView.printFirstTurn()
            } else {
                outputView.printNormalTurn(lastStone.color, position)
            }
            position = inputView.readPosition(turn)
            val stone = getPosition(turn, position)
            board.put(stone)
            if (board.isOmok(stone)) break
            turn.next()
        }
        outputView.showGameResult(turn)
    }

    private fun getPosition(
        turn: Turn,
        inputPosition: Position,
    ): Stone {
        return turn.stone(inputPosition)
    }
}
