package omok.model

sealed interface Turn {
    val stoneType: StoneType

    fun nextTurn(
        point: Point,
        board: Board,
    ): Turn
}

class BlackTurn(private val ruleAdapter: RuleAdapter) : Turn {
    override val stoneType: StoneType = StoneType.BLACK

    override fun nextTurn(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(stoneType, point)
        if (ruleAdapter.checkCanPutStone(board, stone)) return this
        if (ruleAdapter.checkWin(board, stone)) return FinishedTurn(stoneType)
        return WhiteTurn(WhiteRule())
    }
}

class WhiteTurn(private val ruleAdapter: RuleAdapter) : Turn {
    override val stoneType: StoneType = StoneType.WHITE

    override fun nextTurn(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(stoneType, point)
        if (ruleAdapter.checkWin(board, stone)) return FinishedTurn(stoneType)
        return BlackTurn(BlackRule())
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
