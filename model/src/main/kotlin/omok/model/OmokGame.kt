package omok.model

import omok.model.position.Position
import omok.model.result.PutResult
import omok.model.result.ResultHandler
import omok.model.stone.BlackStone
import omok.model.stone.GoStone
import omok.model.stone.WhiteStone.changeStone

class OmokGame {
    fun start(
        readPosition: (GoStone) -> Position,
        drawBoard: () -> Unit,
        printWinner: (GoStone) -> Unit,
    ) {
        var stone: GoStone = BlackStone

        do {
            var isOmok = false
            val position = readPosition(stone)
            val resultState = stone.putStone(position)
            ResultHandler.handleResult(resultState)
            if (resultState != PutResult.Running) {
                continue
            }
            isOmok = stone.findOmok(position)
            showWinner(isOmok, stone, printWinner)
            stone = stone.changeStone()
            drawBoard()
        } while (!isOmok)
    }

    private fun showWinner(
        isOmok: Boolean,
        stone: GoStone,
        printWinner: (GoStone) -> Unit,
    ) {
        if (isOmok) {
            printWinner(stone)
        }
    }
}
