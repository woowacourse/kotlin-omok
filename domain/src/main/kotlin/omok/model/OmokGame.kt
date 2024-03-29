package omok.model

import omok.model.board.Board
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.GoStone
import omok.model.stone.StoneType
import omok.model.stone.WhiteStone
import omok.utils.HandlingExceptionUtils

class OmokGame(private val blackStone: BlackStone, private val whiteStone: WhiteStone) {
    fun start(
        readPosition: (GoStone) -> Position,
        drawBoard: (Board) -> Unit,
        printWinner: (GoStone) -> Unit,
    ) {
        var stone: GoStone = blackStone

        do {
            var isOmok = false
            HandlingExceptionUtils.retryUntilSuccess {
                val (position, currentStone) = putStone(readPosition, stone)
                isOmok = stone.findOmok(position)
                if (isOmok) printWinner(stone)
                stone = changeStone(currentStone)
                drawBoard(Board)
            }
        } while (!isOmok)
    }

    fun changeStone(currentStone: StoneType) = if (currentStone == StoneType.BLACK_STONE) whiteStone else blackStone

    private fun putStone(
        readPosition: (GoStone) -> Position,
        stone: GoStone,
    ): Pair<Position, StoneType> {
        val position = readPosition(stone)
        val currentStone = stone.putStone(position)
        return Pair(position, currentStone)
    }
}
