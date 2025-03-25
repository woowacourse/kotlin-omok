package omok.model.player

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.rule.BlackWinRule
import omok.model.rule.FourFourRule
import omok.model.rule.ThreeThreeRule
import omok.model.rule.WhiteWinRule
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
    ) = BlackWinRule(position, omokBoard).validate() ||
        WhiteWinRule(position, omokBoard).validate()

    private fun isForbidden(
        position: Position,
        omokBoard: OmokBoard,
    ): Boolean = FourFourRule(position, omokBoard).validate() || ThreeThreeRule(position, omokBoard).validate()

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
