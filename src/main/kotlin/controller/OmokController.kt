package controller

import model.AddStoneStatus
import model.GameBoard
import model.Stone
import model.StoneColor
import view.InputView
import view.Message
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
        val stone = Stone.ofOrNull(position, turnColor)
        if (stone == null) {
            outputView.printError(Message.ERROR_POSITION)
            return addValidStone(gameBoard, inputView.errorReInput())
        }
        when (val addStoneStatus = gameBoard.addStone(stone)) {
            AddStoneStatus.IsWin,
            AddStoneStatus.IsAble,
            -> return addStoneStatus

            AddStoneStatus.IsExist -> outputView.printError(Message.EXIST_STONE)
            AddStoneStatus.IsThreeThree -> outputView.printError(Message.THREE_THREE)
            AddStoneStatus.IsFourFour -> outputView.printError(Message.FOUR_FOUR)
            AddStoneStatus.IsOverFive -> outputView.printError(Message.OVER_FIVE)
        }
        return addValidStone(gameBoard)
    }
}
