package omok.model

import omok.model.board.Board
import omok.model.position.Position
import omok.model.result.PutResultUtils.handleHintMessage
import omok.model.result.PutResultUtils.isRunning
import omok.model.stone.BlackStone
import omok.model.stone.GoStone
import omok.model.stone.GoStone.Companion.change

class OmokGame(private val blackStone: BlackStone) {
    fun start(
        readPosition: (GoStone) -> Position,
        drawBoard: (Board) -> Unit,
        printWinner: (GoStone) -> Unit,
        printHintMessage: (String) -> Unit,
    ) {
        var stone: GoStone = blackStone
        var isOmok = false

        do {
            val position = readPosition(stone)
            val putResult = stone.putStone(position)
            if (isRunning(putResult).not()) {
                printHintMessage(handleHintMessage(putResult))
                continue
            }
            isOmok = stone.findOmok(position)
            if (isOmok) printWinner(stone)
            stone = stone.change()
            drawBoard(Board)
        } while (!isOmok)
    }
}
