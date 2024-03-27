import omok.model.board.ColumnNumber
import omok.model.board.CoordsNumber
import omok.model.board.Position
import omok.model.board.Stone
import omok.model.omokGame.OmokGame
import omok.view.InputView
import omok.view.OutputView

object Controller {
    private val outputView = OutputView()
    val game = OmokGame(outputView)

    fun start() {
        game.endGame(game.startGame())
    }

    fun requestPlayerMove(currentStone: Stone) {
        val (rowCoords, columnCoords) = readPlayerCoords(currentStone, "")
        if (rowCoords != null && columnCoords != null &&
            !game.board.isMoveForbidden(
                rowCoords,
                columnCoords,
                @Suppress("ktlint:standard:max-line-length") game.board.findForbiddenPositions(currentStone),
            ) && !game.board.isNotEmpty(rowCoords, columnCoords)
        ) {
            game.placeStone(Position(rowCoords, columnCoords), currentStone)
            outputView.printBoard(game.board.gameBoard, game.board.findForbiddenPositions(game.currentStone))
        } else {
            println("Invalid move. Please try again.")
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
