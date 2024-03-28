import woowacourse.omok.omok.model.board.ColumnNumber
import woowacourse.omok.omok.model.board.CoordsNumber
import woowacourse.omok.omok.model.board.Position
import woowacourse.omok.omok.model.board.Stone
import woowacourse.omok.omok.model.omokGame.OmokGame
import woowacourse.omok.omok.view.InputView
import woowacourse.omok.omok.view.OutputView

object Controller {
    private val outputView = OutputView()
    val omok = OmokGame(outputView)

    fun start(currentStone: Stone) {
        omok.endGame(omok.startGame(currentStone))
    }

    fun requestPlayerMove(
        currentStone: Stone,
        rowCoords: CoordsNumber,
        columnCoords: CoordsNumber,
    ): Boolean {
        if (rowCoords != null && columnCoords != null &&
            !omok.board.isMoveForbidden(
                rowCoords,
                columnCoords,
                omok.renjuGameRule.findForbiddenPositions(currentStone),
            ) && !omok.board.isNotEmpty(rowCoords, columnCoords)
        ) {
            omok.placeStone(Position(rowCoords, columnCoords), currentStone)
            outputView.printBoard(
                omok.board.gameBoard,
                omok.board.findForbiddenPositions(currentStone)
            )
            return true
        } else {
            outputView.printForbiddenMoveMessage()
            return false
        }
    }

    fun isRunning(): Boolean {
        return omok.board.isRunning()
    }

//    private fun readPlayerCoords(
//        currentStone: Stone,
//        previousStoneCoords: String,
//    ): Pair<CoordsNumber?, CoordsNumber?> {
//        val (rowNumber, columnLetter) = InputView.readPlayerMove(currentStone, previousStoneCoords)
//        val columnNumber = ColumnNumber.fromLetter(columnLetter)
//        return Pair(rowNumber, columnNumber)
//    }
}
