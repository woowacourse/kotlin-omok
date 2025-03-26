package omok.model.player

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.rule.OmokRule.Companion.BLACK_STONE
import omok.model.rule.OmokRule.Companion.WHITE_STONE
import omok.model.rule.RenjuRule
import omok.model.rule.WinRule
import omok.model.stone.Stone
import omok.model.stone.StoneColor

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
                isWin(position, omokBoard) -> Win

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
        val blackWin = WinRule(BLACK_STONE, position, omokBoard).validate()
        val whiteWin = WinRule(WHITE_STONE, position, omokBoard).validate()
        return blackWin || whiteWin
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
