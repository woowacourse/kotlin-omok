package woowacourse.omok.domain.model

sealed class Turn {
    abstract val stoneType: StoneType

    fun nextTurn(
        stone: Stone,
        ruleAdapter: RuleAdapter,
    ): Turn {
        if (ruleAdapter.checkForbidden(stone)) return this
        if (ruleAdapter.checkWin(stone)) return FinishedTurn(stoneType)
        return when (stoneType) {
            StoneType.BLACK -> WhiteTurn()
            StoneType.WHITE -> BlackTurn()
            StoneType.EMPTY -> FinishedTurn(stoneType)
        }
    }

    fun getWinner(): String {
        val winner = when (stoneType) {
            StoneType.BLACK -> STONE_TYPE_BLACK
            StoneType.WHITE -> STONE_TYPE_WHITE
            StoneType.EMPTY -> return ""
        }
        return winner
    }

    companion object {
        const val STONE_TYPE_BLACK = "흑"
        const val STONE_TYPE_WHITE = "백"
    }
}

class BlackTurn : Turn() {
    override val stoneType: StoneType = StoneType.BLACK
}

class WhiteTurn : Turn() {
    override val stoneType: StoneType = StoneType.WHITE
}

class FinishedTurn(override val stoneType: StoneType) : Turn()
