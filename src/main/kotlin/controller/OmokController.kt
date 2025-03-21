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
            addValidStone(gameBoard)
            outputView.printGameBoard(gameBoard.stones.toMutableList())
            if (gameBoard.isWin()) break
            turnColor = turnColor.switch()
        }
        outputView.printWinner(turnColor)
    }

    private fun addValidStone(gameBoard: GameBoard) {
        val position = inputView.readInputPosition(turnColor, gameBoard.lastStone())
        val addStoneStatus = gameBoard.addStone(Stone.of(position, turnColor))
        when (addStoneStatus) {
            AddStoneStatus.IsAble -> return
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
