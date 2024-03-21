package omok.model

class OmokGame(private val blackStone: BlackStone, private val whiteStone: WhiteStone) {
    fun startGame(
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
                if (isOmok) {
                    printWinner(stone)
                }
                stone = if (currentStone == Stone.BLACK_STONE) blackStone else whiteStone
                drawBoard(Board)
            }
        } while (!isOmok)
    }

    private fun <T> retryUntilSuccess(action: () -> T): T =
        runCatching {
            action()
        }.getOrElse {
            println(it.localizedMessage)
            retryUntilSuccess(action)
        }
}
