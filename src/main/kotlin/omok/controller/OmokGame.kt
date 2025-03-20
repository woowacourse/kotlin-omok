package omok.controller

import omok.domain.Board
import omok.domain.Stone
import omok.domain.StoneType
import omok.view.InputView
import omok.view.OutputView
import omok.domain.Turn

class OmokGame(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun start() {
        val board = Board()
        val turn = Turn()
        outputView.printStartMessage()
        while (true) {
            outputView.showBoard(board.grid)
            val stone = play(turn)
            board.put(stone)
        }
    }

    private fun play(turn: Turn): Stone {
        if (turn.isBlack()) {
            turn.color = StoneType.BLACK
        }
        if (turn.isWhite()) {
            turn.color = StoneType.WHITE
        }
        val inputPosition = inputView.readPosition()
        return turn.stone(inputPosition)
    }
}