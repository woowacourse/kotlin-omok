package controller

import model.AddStoneStatus
import model.GameBoard
import model.Stone
import model.StoneColor
import view.InputView
import view.Message
import view.Message.ERROR_FORMAT
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

    private fun ResultView.printError(status: AddStoneStatus) {
        print(ERROR_FORMAT)
        when {
            status == AddStoneStatus.IsExist -> print(Message.EXIST_STONE)
            status == AddStoneStatus.IsOverFive -> print(Message.OVER_FIVE)
            status == AddStoneStatus.IsThreeThree -> print(Message.THREE_THREE)
            status == AddStoneStatus.IsFourFour -> print(Message.FOUR_FOUR)
            status == AddStoneStatus.IsUnAblePosition -> print(Message.ERROR_POSITION)
        }
    }
}
