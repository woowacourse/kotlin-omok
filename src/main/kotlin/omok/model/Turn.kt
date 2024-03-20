package omok.model

import omok.model.Rule.isWinCondition

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
        board.putStone(stone)
        if (isWinCondition(board.board, stone)) return FinishedTurn()
        return WhiteTurn(stone)
    }
}

class WhiteTurn(override val before: Stone) : Turn {
    override fun putStone(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(StoneType.WHITE, point)
        board.putStone(stone)
        if (isWinCondition(board.board, stone)) return FinishedTurn()
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
