package omok.model

import omok.model.board.Board
import omok.model.position.Position
import omok.model.result.PutResult
import omok.model.stone.BlackStone
import omok.model.stone.GoStone
import omok.model.stone.StoneType
import omok.model.stone.WhiteStone
import omok.utils.HandlingPutResultUtils

class OmokGame(private val blackStone: BlackStone, private val whiteStone: WhiteStone) {
    fun start(
        readPosition: (GoStone) -> Position,
        drawBoard: (Board) -> Unit,
        printWinner: (GoStone) -> Unit,
    ) {
        var stone: GoStone = blackStone

        do {
            val (position, currentStone) = putStone(readPosition, stone)
            val isOmok = stone.findOmok(position)
            if (isOmok) printWinner(stone)
            stone = changeStone(currentStone)
            drawBoard(Board)
        } while (!isOmok)
    }

    fun changeStone(currentStone: StoneType) = if (currentStone == StoneType.BLACK_STONE) whiteStone else blackStone

    private fun putStone(
        readPosition: (GoStone) -> Position,
        stone: GoStone,
    ): Pair<Position, StoneType> {
        val position = readPosition(stone)
        handleInvalidPosition(position, readPosition, stone)
        handleForbidden(position, readPosition, stone)
        val currentStone = stone.putStone(position)
        return Pair(position, currentStone)
    }

    private fun handleInvalidPosition(
        position: Position,
        readPosition: (GoStone) -> Position,
        stone: GoStone,
    ) {
        val putResult = position.validatePosition()

        if (putResult == PutResult.Failure) {
            HandlingPutResultUtils.displayPutStatus(putResult)
            this.putStone(readPosition, stone)
        }
    }

    private fun handleForbidden(
        position: Position,
        readPosition: (GoStone) -> Position,
        stone: GoStone,
    ) {
        val putResult = position.checkForbidden()

        if (putResult != PutResult.Running) {
            HandlingPutResultUtils.displayPutStatus(putResult)
            this.putStone(readPosition, stone)
        }
    }
}
