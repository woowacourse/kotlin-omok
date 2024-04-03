package woowacourse.omok.domain.model

sealed class Turn {
    abstract val stoneType: StoneType

    abstract fun nextTurn(isWin: Boolean): Turn
}

class BlackTurn : Turn() {
    override val stoneType: StoneType = StoneType.BLACK

    override fun nextTurn(isWin: Boolean): Turn {
        return if (isWin) FinishedTurn(stoneType) else WhiteTurn()
    }
}

class WhiteTurn : Turn() {
    override val stoneType: StoneType = StoneType.WHITE

    override fun nextTurn(isWin: Boolean): Turn {
        return if (isWin) FinishedTurn(stoneType) else BlackTurn()
    }
}

class FinishedTurn(override val stoneType: StoneType) : Turn() {
    override fun nextTurn(isWin: Boolean): Turn {
        return this
    }
}
