package woowacourse.omok.model.player

import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.PositionState
import woowacourse.omok.model.rule.RenjuRule
import woowacourse.omok.model.rule.WinRule
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor

class Turn : GameState() {
    var stone: Stone = Stone(StoneColor.BLACK)
        private set

    fun place(
        position: Position,
        omokBoard: OmokBoard,
    ): GameState {
        omokBoard.placeStone(position, stone)
        gameState =
            when {
                isWin(position, omokBoard) -> {
                    omokBoard.reset()
                    Win
                }

                isForbidden(position, omokBoard) -> {
                    omokBoard.forbidden(position)
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
            if (stone.color == StoneColor.BLACK) PositionState.BLACK_POSITION else PositionState.WHITE_POSITION
        return WinRule(positionState, position, omokBoard).validate()
    }

    private fun isForbidden(
        position: Position,
        omokBoard: OmokBoard,
    ): Boolean = RenjuRule(position, omokBoard).validate()

    fun next() {
        if (gameState == ForbiddenMove) return
        if (gameState == Playing) {
            stone =
                when (stone.color) {
                    StoneColor.BLACK -> Stone(StoneColor.WHITE)
                    StoneColor.WHITE -> Stone(StoneColor.BLACK)
                }
        }
    }
}
