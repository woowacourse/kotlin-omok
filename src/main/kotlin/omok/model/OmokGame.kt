package omok.model

class OmokGame(private val blackStone: BlackStone, private val whiteStone: WhiteStone) {
    fun startGame(
        readPosition: (GoStone) -> Position,
        drawBoard: (Board) -> Unit,
    ) {
        var stone: GoStone = blackStone

        do {
            val position = readPosition(stone)
            val currentStone = stone.putStone(position)
            val isOmok = stone.findOmok(position)
            stone = if (currentStone == Stone.BLACK_STONE) blackStone else whiteStone
            drawBoard(Board)
        } while (!isOmok)
    }
}
