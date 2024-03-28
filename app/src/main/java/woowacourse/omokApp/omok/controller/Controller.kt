import woowacourse.omokApp.omok.model.board.ColumnNumber
import woowacourse.omokApp.omok.model.board.CoordsNumber
import woowacourse.omokApp.omok.model.board.Position
import woowacourse.omokApp.omok.model.board.Stone
import woowacourse.omokApp.omok.model.omokGame.OmokGame
import woowacourse.omokApp.omok.view.InputView
import woowacourse.omokApp.omok.view.OutputView

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
