package omok.model

import omok.model.position.Position
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
            retryUntilSuccess {
                val position = readPosition(stone)
                stone.putStone(position)
                isOmok = stone.findOmok(position)
                showWinner(isOmok, stone, printWinner)
                stone = stone.changeStone()
                drawBoard()
            }
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

    private fun <T> retryUntilSuccess(action: () -> T): T =
        runCatching {
            action()
        }.getOrElse {
            println(it.localizedMessage)
            retryUntilSuccess(action)
        }
}
