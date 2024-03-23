import omok.model.board.ColumnNumber
import omok.model.board.CoordsNumber
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

    private fun playGame(board: Board): Board {
        var currentStone = Stone.BLACK
        var previousStoneCoords = ""
        while (board.isRunning()) {
            val forbiddenPositions = board.findForbiddenPositions(currentStone)
            println(forbiddenPositions)
            OutputView.printBoard(board.gameBoard, forbiddenPositions)
            val (rowCoords, columnCoords) = readPlayerCoords(currentStone, previousStoneCoords)
            if (isWrongCoords(columnCoords, rowCoords)) continue
            if (board.isMoveForbidden(rowCoords!!, columnCoords!!, forbiddenPositions)) continue
            if (board.isNotEmpty(rowCoords, columnCoords)) continue
            board.setStone(rowCoords, columnCoords, currentStone)
            previousStoneCoords = "${(columnCoords.number + 65).toChar()}${rowCoords.number + 1}"
            currentStone = togglePlayer(currentStone)
        }
        OutputView.showWinner(currentStone)
        return board
    }

    private fun readPlayerCoords(
        currentStone: Stone,
        previousStoneCoords: String,
    ): Pair<CoordsNumber?, CoordsNumber?> {
        val (rowNumber, columnLetter) = InputView.readPlayerMove(currentStone, previousStoneCoords)
        val columnNumber = ColumnNumber.fromLetter(columnLetter)
        return Pair(rowNumber, columnNumber)
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
