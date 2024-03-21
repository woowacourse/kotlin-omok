import omok.model.BoardColumn
import omok.model.Omok
import omok.model.Stone
import omok.view.InputView
import omok.view.OutputView

object OmokGameController {
    fun startOmokGame() {
        OutputView.printStartMessage()
        val omok = playGame(Omok())
        OutputView.printBoard(omok.gameBoard)
    }

    private fun playGame(
        omok: Omok,
        startStone: Stone = Stone.BLACK,
    ): Omok {
        var currentStone = startStone
        while (omok.isRunning()) {
            val forbiddenPositions = omok.checkBoard(currentStone.value)
            OutputView.printBoard(omok.gameBoard, forbiddenPositions)

            val (rowNumber, columnLetter) = InputView.readPlayerMove(currentStone)
            if (isWrongCoords(columnLetter, rowNumber)) continue

            val columnNumber = BoardColumn.fromLetter(columnLetter)!!.column.minus(1)

            if (canSetStone(omok, rowNumber, columnNumber, forbiddenPositions)) continue

            omok.setStone(rowNumber, columnNumber, currentStone.value)
            currentStone = togglePlayer(currentStone)
        }
        return omok
    }

    private fun canSetStone(
        omok: Omok,
        rowNumber: Int,
        columnNumber: Int,
        forbiddenPositions: List<Pair<Int, Int>>,
    ): Boolean {
        if (checkEmpty(omok, rowNumber, columnNumber)) return true
        if (checkForbidden(omok, rowNumber, columnNumber, forbiddenPositions)) return true
        return false
    }

    private fun checkForbidden(
        omok: Omok,
        rowNumber: Int,
        columnNumber: Int,
        forbiddenPositions: List<Pair<Int, Int>>,
    ): Boolean {
        if (omok.isForbidden(rowNumber, columnNumber, forbiddenPositions)) {
            OutputView.printForbiddenMoveMessage()
            return true
        }
        return false
    }

    private fun checkEmpty(
        omok: Omok,
        rowNumber: Int,
        columnNumber: Int,
    ): Boolean {
        if (omok.isNotEmpty(rowNumber, columnNumber)) {
            OutputView.printOccupiedPositionMessage()
            return true
        }
        return false
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
