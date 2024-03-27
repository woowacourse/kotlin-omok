import omok.library.RenjuRule
import omok.model.Black
import omok.model.CoordsNumber
import omok.model.OmokGame
import omok.model.OmokStone
import omok.model.White
import omok.view.InputView
import omok.view.OutputView

class OmokGameController {
    fun startOmokGame() {
        OutputView.printStartMessage()
        val omokGame = playGame(OmokGame(rule = RenjuRule))
        OutputView.printBoard(omokGame.gameBoard)
    }

    private fun playGame(omokGame: OmokGame): OmokGame {
        while (omokGame.isRunning()) {
            OutputView.printBoard(omokGame.gameBoard, omokGame.getForbiddenPositions())
            val currentStone = readCurrentStone(omokGame.getCurrentStone())
            val result = omokGame.setStone(currentStone)
        }
        return omokGame
    }

    private fun readCurrentStone(previousOmokStone: OmokStone?): OmokStone {
        val currentColor = if (previousOmokStone is Black) "백" else "흑"
        val playerInput = InputView.readPlayerInput(currentColor, convert(previousOmokStone))

        val columnNumber = CoordsNumber.fromLetter(playerInput[0].uppercaseChar())
        val rowNumber = CoordsNumber.fromNumber(playerInput.substring(1).toInt() - 1)

        if (columnNumber != null && rowNumber != null) {
            return when (currentColor) {
                "흑" -> Black(rowNumber, columnNumber)
                "백" -> White(rowNumber, columnNumber)
                else -> throw IllegalStateException("Unexpected color: $currentColor")
            }
        } else {
            return readCurrentStone(previousOmokStone)
        }
    }

    private fun convert(omokStone: OmokStone?): String {
        if (omokStone == null) return ""
        return (omokStone.columnCoords.number + 65).toChar() + (omokStone.rowCoords.number + 1).toString()
    }
}
