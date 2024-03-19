package omok.model

sealed interface Turn {
    fun putStone(
        point: Point,
        board: Board,
    ): Turn
}

class BlackTurn : Turn {
    override fun putStone(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(StoneType.BLACK, point)
        board.putStone(stone)
        return WhiteTurn()
    }
}

class WhiteTurn() : Turn {
    override fun putStone(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(StoneType.WHITE, point)
        board.putStone(stone)
        return BlackTurn()
    }
}
