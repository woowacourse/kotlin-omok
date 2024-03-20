package omok.model

class Player(val color: Color) {
    private var _isWin = false
    val isWin: Boolean
        get() = _isWin

    fun playTurn(
        board: Board,
        coordinate: Coordinate,
    ) {
        val stone = Stone(color, coordinate)
        putStoneOnBoard(board, stone)
        _isWin = checkOmok(board.stones, stone)
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
