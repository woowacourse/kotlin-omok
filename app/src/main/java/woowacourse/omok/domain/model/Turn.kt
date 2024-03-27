package woowacourse.omok.domain.model

sealed class Turn {
    abstract val stoneType: StoneType

    fun nextTurn(
        point: Point,
        ruleAdapter: RuleAdapter,
    ): Turn {
        val stone = Stone(stoneType, point)
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
        return MESSAGE_WINNER.format(winner)
    }

    companion object {
        private const val STONE_TYPE_BLACK = "흑"
        private const val STONE_TYPE_WHITE = "백"
        private const val MESSAGE_WINNER = "%s돌이 승리했습니다!!"
    }
}

class BlackTurn : Turn() {
    override val stoneType: StoneType = StoneType.BLACK
}

class WhiteTurn : Turn() {
    override val stoneType: StoneType = StoneType.WHITE
}

class FinishedTurn(override val stoneType: StoneType) : Turn()
