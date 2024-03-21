import omok.model.BoardColumn
import omok.model.Omok
import omok.model.Stone
import omok.view.InputView
import omok.view.OutputView

object OmokGameController {
    fun startOmokGame() {
        OutputView.printStartMessage()
        val omok = Omok()
        var currentStone = Stone.BLACK

        while (omok.isRunning()) {
            val forbiddenPositions = omok.checkBoard(currentStone.value)
            OutputView.printBoard(omok.gameBoard, forbiddenPositions)

            val (rowNumber, columnLetter) = InputView.readPlayerMove(currentStone)
            if (isWrongCoords(columnLetter, rowNumber)) continue

            val columnNumber = BoardColumn.fromLetter(columnLetter)!!.column.minus(1)

            if (omok.isNotEmpty(rowNumber, columnNumber)) {
                OutputView.printOccupiedPositionMessage()
                continue
            }
            if (omok.isForbidden(rowNumber, columnNumber, forbiddenPositions)) {
                OutputView.printForbiddenMoveMessage()
                continue
            }
            omok.setStone(rowNumber, columnNumber, currentStone.value)
            currentStone = togglePlayer(currentStone)
        }

        OutputView.printBoard(omok.gameBoard)
    }

    private fun isWrongCoords(
        columnLetter: Char,
        rowNumber: Int,
    ): Boolean {
        if (BoardColumn.fromLetter(columnLetter) == null || rowNumber !in (0..14)) {
            OutputView.printWrongPositionMessage()
            return true
        }
        return false
    }

    private fun togglePlayer(currentStone: Stone): Stone = if (currentStone == Stone.BLACK) Stone.WHITE else Stone.BLACK
}
