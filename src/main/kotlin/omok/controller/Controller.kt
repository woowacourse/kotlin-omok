import omok.model.board.ColumnNumber
import omok.model.board.CoordsNumber
import omok.model.board.Position
import omok.model.board.Stone
import omok.model.omokGame.OmokGame
import omok.view.InputView
import omok.view.OutputView

object Controller {
    private val outputView = OutputView()
    val omok = OmokGame(outputView)

    fun start() {
        omok.endGame(omok.startGame())
    }

    fun requestPlayerMove(currentStone: Stone) {
        val (rowCoords, columnCoords) = readPlayerCoords(currentStone, "")
        if (rowCoords != null && columnCoords != null &&
            !omok.board.isMoveForbidden(
                rowCoords,
                columnCoords,
                omok.renjuGameRule.findForbiddenPositions(currentStone),
            ) && !omok.board.isNotEmpty(rowCoords, columnCoords)
        ) {
            omok.placeStone(Position(rowCoords, columnCoords), currentStone)
            outputView.printBoard(omok.board.gameBoard, omok.board.findForbiddenPositions(omok.currentStone))
        } else {
            outputView.printForbiddenMoveMessage()
            requestPlayerMove(currentStone)
        }
    }

    private fun readPlayerCoords(
        currentStone: Stone,
        previousStoneCoords: String,
    ): Pair<CoordsNumber?, CoordsNumber?> {
        val (rowNumber, columnLetter) = InputView.readPlayerMove(currentStone, previousStoneCoords)
        val columnNumber = ColumnNumber.fromLetter(columnLetter)
        return Pair(rowNumber, columnNumber)
    }
}
