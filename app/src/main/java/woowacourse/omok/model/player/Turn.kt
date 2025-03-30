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
    ): GameState {
        omokBoard.placeStone(position, currentStoneColor)
        gameState =
            when {
                isWin(position, omokBoard) -> {
                    omokBoard.reset()
                    Win
                }

                isForbidden(position, omokBoard) -> {
                    omokBoard.forbidden(currentStoneColor, position)
                    ForbiddenMove
                }

                else -> Playing
            }
        return gameState
    }

    private fun isWin(
        position: Position,
        omokBoard: OmokBoard,
    ): Boolean {
        val positionState =
            if (currentStoneColor == StoneColor.BLACK) PositionState.BLACK_POSITION else PositionState.WHITE_POSITION
        return WinRule(positionState, position, omokBoard).validate()
    }

    private fun isForbidden(
        position: Position,
        omokBoard: OmokBoard,
    ): Boolean = RenjuRule(position, omokBoard).validate() && currentStoneColor == StoneColor.BLACK

    fun next() {
        if (gameState == ForbiddenMove) return
        if (gameState == Playing) {
            stone =
                when (currentStoneColor) {
                    StoneColor.BLACK -> Stone(StoneColor.WHITE)
                    StoneColor.WHITE -> Stone(StoneColor.BLACK)
                }
        }
        turnDao?.saveTurn(currentStoneColor)
    }
}
