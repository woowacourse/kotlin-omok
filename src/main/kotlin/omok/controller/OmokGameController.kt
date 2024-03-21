import omok.model.board.ColumnNumber
import omok.model.board.CoordsNumber
import omok.model.board.Stone
import omok.model.omokGame.Omok
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
            val forbiddenPositions = omok.checkBoard(currentStone.color)
            OutputView.printBoard(omok.gameBoard, forbiddenPositions)
            val (rowNumber, columnNumber) = convertToCoords(currentStone)
            if (isWrongCoords(columnNumber, rowNumber)) continue
            if (canSetStone(omok, rowNumber!!.number, columnNumber!!.number, forbiddenPositions)) continue
            omok.setStone(rowNumber, columnNumber, currentStone)
            currentStone = togglePlayer(currentStone)
        }
        return omok
    }

    private fun convertToCoords(currentStone: Stone): Pair<CoordsNumber?, CoordsNumber?> {
        val (rowLetter, columnLetter) = InputView.readPlayerMove(currentStone)
        val rowNumber = CoordsNumber.of(rowLetter)
        val columnNumber = ColumnNumber.fromLetter(columnLetter)
        return Pair(rowNumber, columnNumber)
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
        columnNumber: CoordsNumber?,
        rowNumber: CoordsNumber?,
    ): Boolean {
        if (columnNumber == null || rowNumber == null) {
            OutputView.printWrongPositionMessage()
            return true
        }
        return false
    }

    private fun togglePlayer(currentStone: Stone): Stone = if (currentStone == Stone.BLACK) Stone.WHITE else Stone.BLACK
}
