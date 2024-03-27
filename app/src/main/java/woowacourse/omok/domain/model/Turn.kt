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
}

class BlackTurn : Turn() {
    override val stoneType: StoneType = StoneType.BLACK
}

class WhiteTurn : Turn() {
    override val stoneType: StoneType = StoneType.WHITE
}

class FinishedTurn(override val stoneType: StoneType) : Turn()
