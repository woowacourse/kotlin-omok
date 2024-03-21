import omok.model.BoardColumn
import omok.model.Omok
import omok.model.Stone
import omok.view.InputView
import omok.view.OutputView

object OmokGameController {
    fun startOmokGame() {
        var input = ""
        OutputView.printStartMessage()
        val omok = Omok()
        var currentStone = Stone.BLACK
        while (omok.isRunning()) {
            val forbiddenPositions = omok.checkBoard(currentStone.value)
            OutputView.printBoard(omok.gameBoard, forbiddenPositions)
            input = InputView.readPlayerInput(currentStone, input)
            val columnLetter = (input[0].uppercase())[0]
            val columnNumber = input.substring(1).toInt() - 1
            val rowNumber = BoardColumn.fromLetter(columnLetter)?.column!! - 1
            if (omok.gameBoard[columnNumber][rowNumber] != Omok.EMPTY) {
                OutputView.printOccupiedPositionMessage()
            } else if (columnNumber to rowNumber in forbiddenPositions) {
                OutputView.printForbiddenMoveMessage()
            } else {
                omok.setStone(rowNumber, columnNumber, currentStone.value)
                currentStone = togglePlayer(currentStone)
            }
        }
        OutputView.printBoard(omok.gameBoard)
    }

    private fun togglePlayer(currentStone: Stone): Stone {
        return if (currentStone == Stone.BLACK) Stone.WHITE else Stone.BLACK
    }
}
