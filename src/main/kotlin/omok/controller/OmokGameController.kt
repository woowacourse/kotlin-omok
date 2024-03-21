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
        var previousStoneCoords = ""

        while (omok.isRunning()) {
            val forbiddenPositions = omok.checkBoard(currentStone)
            OutputView.printBoard(omok.gameBoard, forbiddenPositions)
            val (rowCoords, columnCoords) = readPlayerCoords(currentStone, previousStoneCoords)
            if (isWrongCoords(columnCoords, rowCoords)) continue
            if (canSetStone(omok, rowCoords!!, columnCoords!!, forbiddenPositions)) continue
            omok.setStone(rowCoords, columnCoords, currentStone)
            previousStoneCoords = (columnCoords.number + 65).toChar() + (rowCoords.number + 1).toString()
            omok.isGameOver(rowCoords, columnCoords, currentStone)
            currentStone = togglePlayer(currentStone)
        }
        return omok
    }

    private fun readPlayerCoords(
        currentStone: Stone,
        previousStoneCoords: String,
    ): Pair<CoordsNumber?, CoordsNumber?> {
        val (rowLetter, columnLetter) = InputView.readPlayerMove(currentStone, previousStoneCoords)
        val rowNumber = CoordsNumber.of(rowLetter)
        val columnNumber = ColumnNumber.fromLetter(columnLetter)
        return Pair(rowNumber, columnNumber)
    }

    private fun canSetStone(
        omok: Omok,
        rowNumber: CoordsNumber,
        columnNumber: CoordsNumber,
        forbiddenPositions: List<Pair<CoordsNumber, CoordsNumber>>,
    ): Boolean {
        if (checkEmpty(omok, rowNumber, columnNumber)) return true
        if (checkForbidden(omok, rowNumber, columnNumber, forbiddenPositions)) return true
        return false
    }

    private fun checkForbidden(
        omok: Omok,
        rowNumber: CoordsNumber,
        columnNumber: CoordsNumber,
        forbiddenPositions: List<Pair<CoordsNumber, CoordsNumber>>,
    ): Boolean {
        if (omok.isForbidden(rowNumber, columnNumber, forbiddenPositions)) {
            OutputView.printForbiddenMoveMessage()
            return true
        }
        return false
    }

    private fun checkEmpty(
        omok: Omok,
        rowNumber: CoordsNumber,
        columnNumber: CoordsNumber,
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
