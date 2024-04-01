package woowacourse.omok.domain.model

sealed class Turn {
    abstract val stoneType: StoneType

    fun nextTurn(
        point: Point,
        ruleAdapter: RuleAdapter,
    ): Turn {
        if (ruleAdapter.checkForbidden(point, stoneType)) return this
        if (ruleAdapter.checkWin(point, stoneType)) return FinishedTurn(stoneType)
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
