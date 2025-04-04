package woowacourse.omok.model.player

import woowacourse.omok.db.TurnDao
import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.PositionState
import woowacourse.omok.model.rule.RenjuRule
import woowacourse.omok.model.rule.WinRule
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor

class Turn(private val turnDao: TurnDao? = null) : GameState() {
    var stone: Stone
        private set

    init {
        val stoneColor = turnDao?.getLastTurn() ?: StoneColor.BLACK
        this.stone = Stone(stoneColor)
    }

    val currentStoneColor
        get() = stone.color

    fun place(
        position: Position,
        omokBoard: OmokBoard,
    ): Boolean {
        if (omokBoard.successPlaceStone(position, currentStoneColor)) {
            gameState =
                when {
                    isWin(position, omokBoard) -> {
                        omokBoard.reset()
                        Win
                    }

                    isForbidden(RenjuRule(position, omokBoard).validateDoubleThree()) -> {
                        omokBoard.forbidden(currentStoneColor, position)
                        ForbiddenMove.DoubleThree
                    }

                    isForbidden(RenjuRule(position, omokBoard).validateDoubleFour()) -> {
                        omokBoard.forbidden(currentStoneColor, position)
                        ForbiddenMove.DoubleFour
                    }

                    else -> Playing
                }
            return true
        }
        return false
    }

    fun next() {
        if (forbidden()) return
        if (gameState == Playing) {
            stone =
                when (currentStoneColor) {
                    StoneColor.BLACK -> Stone(StoneColor.WHITE)
                    StoneColor.WHITE -> Stone(StoneColor.BLACK)
                }
        }
        turnDao?.saveTurn(currentStoneColor)
    }

    private fun isWin(
        position: Position,
        omokBoard: OmokBoard,
    ): Boolean {
        val positionState =
            if (currentStoneColor == StoneColor.BLACK) PositionState.BLACK_POSITION else PositionState.WHITE_POSITION
        return WinRule(positionState, position, omokBoard).win()
    }

    private fun isForbidden(validateForbidden: Boolean): Boolean = validateForbidden && currentStoneColor == StoneColor.BLACK
}
