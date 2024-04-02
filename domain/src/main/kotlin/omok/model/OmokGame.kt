package omok.model

import omok.model.board.Board
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.GoStone
import omok.model.stone.WhiteStone
import omok.utils.PutResultUtils.isRunning

class OmokGame(private val blackStone: BlackStone, private val whiteStone: WhiteStone) {
    fun start(
        readPosition: (GoStone) -> Position,
        drawBoard: (Board) -> Unit,
        printWinner: (GoStone) -> Unit,
    ) {
        var stone: GoStone = blackStone
        var isOmok = false

        do {
            val position = readPosition(stone)
            val putResult = stone.putStone(position)
            if (isRunning(putResult).not()) continue
            isOmok = stone.findOmok(position)
            if (isOmok) printWinner(stone)
            stone = changeStone(stone)
            drawBoard(Board)
        } while (!isOmok)
    }

    fun changeStone(currentStone: GoStone) = if (currentStone == blackStone) whiteStone else blackStone
}
