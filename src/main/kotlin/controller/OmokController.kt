package controller

import AddStoneStatus
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
            val addStoneStatus = addValidStone(gameBoard)
            outputView.printGameBoard(gameBoard.stones.toMutableList())
            if (addStoneStatus == AddStoneStatus.IsWin) break
            turnColor = turnColor.switch()
        }
        outputView.printWinner(turnColor)
    }

    private fun addValidStone(gameBoard: GameBoard): AddStoneStatus {
        val position = inputView.readInputPosition(turnColor, gameBoard.lastStone())
        when (val addStoneStatus = gameBoard.addStone(Stone.of(position, turnColor))) {
            AddStoneStatus.IsWin,
            AddStoneStatus.IsAble,
            -> return addStoneStatus

            AddStoneStatus.IsExist ->
                run {
                    outputView.printError(ResultView.EXIST_STONE)
                    return addValidStone(gameBoard)
                }

            AddStoneStatus.IsThreeThree ->
                run {
                    outputView.printError(ResultView.THREE_THREE)
                    return addValidStone(gameBoard)
                }

            AddStoneStatus.IsFourFour ->
                run {
                    outputView.printError(ResultView.FOUR_FOUR)
                    return addValidStone(gameBoard)
                }
        }
    }
}
