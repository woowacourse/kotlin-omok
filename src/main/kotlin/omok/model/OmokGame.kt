package omok.model

import omok.model.board.Board
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.GoStone
import omok.model.stone.StoneType
import omok.model.stone.WhiteStone

class OmokGame(private val blackStone: BlackStone, private val whiteStone: WhiteStone) {
    fun start(
        readPosition: (GoStone) -> Position,
        drawBoard: (Board) -> Unit,
        printWinner: (GoStone) -> Unit,
    ) {
        var stone: GoStone = blackStone

        do {
            var isOmok = false
            retryUntilSuccess {
                val position = readPosition(stone)
                val currentStone = stone.putStone(position)
                isOmok = stone.findOmok(position)
                showWinner(isOmok, stone, printWinner)
                stone = if (currentStone == StoneType.BLACK_STONE) blackStone else whiteStone
                drawBoard(Board)
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
