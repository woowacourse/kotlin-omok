import omok.model.board.ColumnNumber
import omok.model.board.CoordsNumber
import omok.model.board.Position
import omok.model.board.Stone
import omok.model.omokGame.Board
import omok.view.InputView
import omok.view.OutputView

object OmokGameController {
    fun startOmokGame() {
        OutputView.printStartMessage()
        val board = playGame(Board())
        OutputView.printBoard(board.gameBoard)
    }

    private fun playGame(
        board: Board,
        startStone: Stone = Stone.BLACK,
    ): Board {
        var currentStone = startStone
        var previousStoneCoords = ""
        while (board.isRunning()) {
            val forbiddenPositions = board.findForbiddenPositions(currentStone)
            OutputView.printBoard(board.gameBoard, forbiddenPositions)
            val (rowCoords, columnCoords) = readPlayerCoords(currentStone, previousStoneCoords)
            if (isWrongCoords(columnCoords, rowCoords)) continue
            if (canSetStone(board, rowCoords!!, columnCoords!!, forbiddenPositions)) continue
            board.setStone(rowCoords, columnCoords, currentStone)
            previousStoneCoords = (columnCoords.number + 65).toChar() + (rowCoords.number + 1).toString()
            if (board.isGameOver(rowCoords, columnCoords, currentStone)) break
            currentStone = togglePlayer(currentStone)
        }
        OutputView.showWinner(currentStone)
        return board
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
        board: Board,
        rowNumber: CoordsNumber,
        columnNumber: CoordsNumber,
        forbiddenPositions: List<Position>,
    ): Boolean {
        if (checkEmpty(board, rowNumber, columnNumber)) return true
        if (checkForbidden(board, rowNumber, columnNumber, forbiddenPositions)) return true
        return false
    }

    private fun checkForbidden(
        board: Board,
        rowNumber: CoordsNumber,
        columnNumber: CoordsNumber,
        forbiddenPositions: List<Position>,
    ): Boolean {
        if (board.isForbidden(rowNumber, columnNumber, forbiddenPositions)) {
            OutputView.printForbiddenMoveMessage()
            return true
        }
        return false
    }

    private fun checkEmpty(
        board: Board,
        rowNumber: CoordsNumber,
        columnNumber: CoordsNumber,
    ): Boolean {
        if (board.isNotEmpty(rowNumber, columnNumber)) {
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
