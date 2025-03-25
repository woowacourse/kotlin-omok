package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.rule.BlackWinRule
import omok.model.rule.FourFourRule
import omok.model.rule.OmokAdapter
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
        val adaptedBoard = OmokAdapter.adaptOmokBoard(omokBoard)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(position)
        gameState =
            when {
                isWin(adaptedBoard, adaptedPoint) -> Win

                isForbidden(adaptedPoint, adaptedBoard) -> {
                    omokBoard.forbidden(position)
                    ForbiddenMove
                }

                else -> Playing
            }
        return gameState
    }

    private fun isWin(
        adaptedBoard: List<List<Int>>,
        adaptedPoint: Pair<Int, Int>,
    ) = BlackWinRule.validate(adaptedBoard, adaptedPoint) ||
        WhiteWinRule.validate(
            adaptedBoard,
            adaptedPoint,
        )

    private fun isForbidden(
        adaptedPoint: Pair<Int, Int>,
        adaptedBoard: List<List<Int>>,
    ): Boolean = FourFourRule.validate(adaptedBoard, adaptedPoint) || ThreeThreeRule.validate(adaptedBoard, adaptedPoint)

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
