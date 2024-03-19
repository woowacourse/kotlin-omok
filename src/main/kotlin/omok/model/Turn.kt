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
        if (board.putStone(stone)) return FinishedTurn()
        return WhiteTurn()
    }
}

class WhiteTurn() : Turn {
    override fun putStone(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(StoneType.WHITE, point)
        if (board.putStone(stone)) return FinishedTurn()
        return BlackTurn()
    }
}

class FinishedTurn : Turn {
    override fun putStone(
        point: Point,
        board: Board,
    ): Turn {
        throw IllegalStateException()
    }
}
