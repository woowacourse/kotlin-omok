package omok.model

sealed interface Turn {
    fun putStone(
        stone: Stone,
        board: Board,
    ): Turn
}

class BlackTurn : Turn {
    override fun putStone(
        stone: Stone,
        board: Board,
    ): Turn {
        board.putStone(stone)
        return WhiteTurn()
    }
}

class WhiteTurn() : Turn {
    override fun putStone(
        stone: Stone,
        board: Board,
    ): Turn {
        board.putStone(stone)
        return BlackTurn()
    }
}
