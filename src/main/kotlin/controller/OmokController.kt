package controller

import GameBoard
import Stone
import StoneColor
import view.InputView
import view.ResultView

class OmokController(
    private val inputView: InputView,
    private val outputView: ResultView,
) {
    private var turnColor: StoneColor = StoneColor.BLACK

    fun run() {
        outputView.printGameStartMessage()
        val gameBoard = GameBoard()

        while (true) {
            addValidStone(gameBoard)
            turnColor = turnColor.switch()
        }
    }

    private tailrec fun addValidStone(gameBoard: GameBoard) {
        val position = inputView.readInputPosition(turnColor, gameBoard.lastStone())
        if (!gameBoard.addStone(Stone.of(position, turnColor))) addValidStone(gameBoard)
    }
}
