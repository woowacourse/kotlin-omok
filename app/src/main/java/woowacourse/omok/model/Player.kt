package omok.model

class Player(val color: Color) {
    private var _isWin = false
    val isWin: Boolean
        get() = _isWin

    fun playTurn(
        board: Board,
        coordinate: Coordinate,
    ): StoneState {
        val stone = Stone(color, coordinate)
        val stoneState = putStoneOnBoard(board, stone)
        _isWin = checkOmok(board.stones, stone)
        return stoneState
    }

    private fun putStoneOnBoard(
        board: Board,
        stone: Stone,
    ): StoneState {
        return board.putStone(stone)
    }

    private fun checkOmok(
        stones: Stones,
        stone: Stone,
    ): Boolean {
        return stones.findOmok(stone)
    }
}
