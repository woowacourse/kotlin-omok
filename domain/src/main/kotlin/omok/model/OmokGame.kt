package omok.model

import omok.model.board.Board
import omok.model.position.Position
import omok.model.result.PutResultUtils.handleHintMessage
import omok.model.result.PutResultUtils.isOmok
import omok.model.result.PutResultUtils.isRunning
import omok.model.stone.BlackStone
import omok.model.stone.GoStone
import omok.model.stone.GoStone.Companion.change

class OmokGame(private val blackStone: BlackStone) {
    fun start(
        readPosition: (GoStone) -> Position,
        drawBoard: (Board) -> Unit,
        printHintMessage: (String) -> Unit,
    ) {
        var stone: GoStone = blackStone

        while (true) {
            val position = readPosition(stone)
            var putResult = stone.putStone(position)
            if (isRunning(putResult).not()) {
                printHintMessage(handleHintMessage(putResult))
                continue
            }
            putResult = stone.findOmok(position)
            drawBoard(Board)
            if (isOmok(putResult)) {
                printHintMessage(handleHintMessage(putResult).format(stone.stoneType.type))
                break
            }
            stone = stone.change()
        }
    }
}
