package omok.model

import omok.rule.BlackRule
import omok.rule.WhiteRule

sealed interface Turn {
    val stoneType: StoneType

    fun nextTurn(
        point: Point,
        board: Board,
    ): Turn
}

class BlackTurn : Turn {
    override val stoneType: StoneType = StoneType.BLACK

    override fun nextTurn(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(stoneType, point)
        if (BlackRule.isForbidden(board, stone)) return this
        if (BlackRule.isWinCondition(board, stone)) return FinishedTurn(stoneType)
        return WhiteTurn()
    }
}

class WhiteTurn : Turn {
    override val stoneType: StoneType = StoneType.WHITE

    override fun nextTurn(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(stoneType, point)
        if (WhiteRule.isWinCondition(board, stone)) return FinishedTurn(stoneType)
        return BlackTurn()
    }
}

class FinishedTurn(override val stoneType: StoneType) : Turn {
    override fun nextTurn(
        point: Point,
        board: Board,
    ): Turn {
        throw IllegalStateException()
    }
}
