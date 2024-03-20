package omok.model

sealed interface Turn {
    val before: Stone?

    fun putStone(
        point: Point,
        board: Board,
    ): Turn
}

class BlackTurn(override val before: Stone? = null) : Turn {
    override fun putStone(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(StoneType.BLACK, point)
        if (board.putStone(stone)) return FinishedTurn()
        return WhiteTurn(stone)
    }
}

class WhiteTurn(override val before: Stone) : Turn {
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
    override val before: Stone? = null

    override fun putStone(
        point: Point,
        board: Board,
    ): Turn {
        throw IllegalStateException()
    }
}
