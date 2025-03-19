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
        outputView.printGameBoard(gameBoard.stones.toMutableList())
        while (true) {
            addValidStone(gameBoard)
            outputView.printGameBoard(gameBoard.stones.toMutableList())
            if (gameBoard.isWin()) break
            turnColor = turnColor.switch()
        }
        outputView.printWinner(turnColor)
    }

    private tailrec fun addValidStone(gameBoard: GameBoard) {
        val position = inputView.readInputPosition(turnColor, gameBoard.lastStone())
        if (!gameBoard.addStone(Stone.of(position, turnColor))) addValidStone(gameBoard)
    }
}
