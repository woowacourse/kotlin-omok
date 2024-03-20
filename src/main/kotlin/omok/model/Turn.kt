package omok.model

sealed interface Turn {
    fun putStone(
        point: Point,
        board: Board,
    ): Turn
}

class BlackTurn(val before: Stone? = null) : Turn {
    override fun putStone(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(StoneType.BLACK, point)
        if (board.putStone(stone)) return FinishedTurn()
        return WhiteTurn(stone)
    }
}

class WhiteTurn(val before: Stone) : Turn {
    override fun putStone(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(StoneType.WHITE, point)
        if (board.putStone(stone)) return FinishedTurn()
        return BlackTurn(stone)
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
