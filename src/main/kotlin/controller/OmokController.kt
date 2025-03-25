package controller

import model.AddStoneStatus
import model.GameBoard
import model.Stone
import model.StoneColor
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
            val addStoneStatus = addValidStone(gameBoard)
            outputView.printGameBoard(gameBoard.stones.toMutableList())
            if (addStoneStatus == AddStoneStatus.IsWin) break
            turnColor = turnColor.switch()
        }
        outputView.printWinner(turnColor)
    }

    private fun addValidStone(
        gameBoard: GameBoard,
        position: String = inputView.readInputPosition(turnColor, gameBoard.lastStone()),
    ): AddStoneStatus {
        when (val addStoneStatus = gameBoard.addStone(Stone.ofOrNull(position, turnColor))) {
            AddStoneStatus.IsWin,
            AddStoneStatus.IsAble,
            -> return addStoneStatus
            else -> outputView.printError(addStoneStatus)
        }
        return addValidStone(gameBoard, inputView.errorReInput())
    }
}
