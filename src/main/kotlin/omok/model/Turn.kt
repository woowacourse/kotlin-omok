package omok.model

import omok.rule.BlackRule
import omok.rule.WhiteRule

sealed interface Turn {
    val before: Stone?

    fun nextTurn(
        point: Point,
        board: Board,
    ): Turn
}

class BlackTurn(override val before: Stone? = null) : Turn {
    override fun nextTurn(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(StoneType.BLACK, point)
        if (BlackRule.isForbidden(board, stone)) return this
        if (BlackRule.isWinCondition(board, stone)) return FinishedTurn(stone)
        return WhiteTurn(stone)
    }
}

class WhiteTurn(override val before: Stone) : Turn {
    override fun nextTurn(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(StoneType.WHITE, point)
        if (WhiteRule.isWinCondition(board, stone)) return FinishedTurn(stone)
        return BlackTurn(stone)
    }
}

class FinishedTurn(override val before: Stone) : Turn {
    override fun nextTurn(
        point: Point,
        board: Board,
    ): Turn {
        throw IllegalStateException()
    }
}
