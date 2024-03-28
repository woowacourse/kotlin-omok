package woowacourse.omok.domain.model

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
        if (board.isForbidden(stone) || point in board) return this
        board.putStone(stone)
        if (board.isWinCondition(stone)) return FinishedTurn(stone)
        return WhiteTurn()
    }
}

class WhiteTurn : Turn {
    override fun putStone(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(StoneType.WHITE, point)
        if (point in board) return this
        board.putStone(stone)
        if (board.isWinCondition(stone)) return FinishedTurn(stone)
        return BlackTurn()
    }
}

class FinishedTurn(val beforeStone: Stone) : Turn {
    override fun putStone(
        point: Point,
        board: Board,
    ): Turn {
        throw IllegalStateException()
    }
}
