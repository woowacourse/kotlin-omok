package omok.model

class Player(val color: Color) {
    var isWin: Boolean = false
        private set

    fun playTurn(
        board: Board,
        coordinate: Coordinate,
    ) {
        val stone = Stone(color, coordinate)
        putStoneOnBoard(board, stone)
        isWin = checkOmok(board.stones, stone)
    }

    private fun putStoneOnBoard(
        board: Board,
        stone: Stone,
    ) {
        board.putStone(stone)
    }

    private fun checkOmok(
        stones: Stones,
        stone: Stone,
    ): Boolean {
        return stones.findOmok(stone)
    }
}
