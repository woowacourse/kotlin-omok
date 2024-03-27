import omok.library.RenjuRule
import omok.model.BoardCoordinate
import omok.model.BoardPosition
import omok.model.OmokGame
import omok.model.OmokStone
import omok.model.OmokStoneType
import omok.view.InputView
import omok.view.OutputView

class OmokGameController {
    fun startOmokGame() {
        OutputView.printStartMessage()
        val omokGame = OmokGame(rule = RenjuRule)
        playGame(omokGame)
        OutputView.printOmokGameBoard(omokGame.getBoard())
    }

    private fun playGame(omokGame: OmokGame) {
        while (omokGame.isRunning()) {
            OutputView.printOmokGameBoard(omokGame.getBoard(), omokGame.getForbiddenPositions())
            val isPlacementSuccessful = placeStone(omokGame)
            if (!isPlacementSuccessful) {
                OutputView.printInvalidPositionMessage()
            }
        }
    }

    private fun placeStone(omokGame: OmokGame): Boolean {
        val stonePosition = getStonePositionToPlace(omokGame)
        val stone = omokGame.generateNextOmokStone(stonePosition)
        return omokGame.placeStoneOnBoard(stone)
    }

    private fun getStonePositionToPlace(omokGame: OmokGame): BoardPosition {
        while (true) {
            val playerInput = readPlayerInputForPosition(omokGame)
            val position = formatPositionFromInput(playerInput)
            if (position != null) return position
        }
    }

    private fun readPlayerInputForPosition(omokGame: OmokGame): String {
        return InputView.readStonePosition(
            formatStoneType(omokGame.getNextStoneType()),
            formatStonePosition(omokGame.getCurrentStone()),
        )
    }

    private fun formatPositionFromInput(input: String): BoardPosition? {
        val col = BoardCoordinate.from(input[0].uppercaseChar())
        val row = BoardCoordinate.from(input.substring(1).toInt() - 1)

        if (col == null || row == null) {
            OutputView.printWrongPositionMessage()
            return null
        }
        return BoardPosition(row, col)
    }

    private fun formatStoneType(stoneType: OmokStoneType): String {
        if (stoneType == OmokStoneType.WHITE) return "백"
        return "흑"
    }

    private fun formatStonePosition(omokStone: OmokStone?): String? {
        if (omokStone == null) return null
        return (omokStone.boardPosition.getColumn() + 65).toChar() + (omokStone.boardPosition.getRow() + 1).toString()
    }
}
