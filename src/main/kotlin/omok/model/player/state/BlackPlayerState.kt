package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.rule.BlackWinRule
import omok.model.rule.FourFourRule
import omok.model.rule.OmokAdapter
import omok.model.rule.ThreeThreeRule
import omok.model.stone.Stone
import omok.model.stone.StoneColor

class BlackPlayerState :
    GameState(),
    PlayerState {
    override val stone = Stone(StoneColor.BLACK)

    override fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
    ): GameState {
        omokBoard.placeStone(position, stone)
        val adaptedBoard = OmokAdapter.adaptOmokBoard(omokBoard)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(position)
        return when {
            BlackWinRule.validate(adaptedBoard, adaptedPoint) -> Win
            isForbidden(adaptedPoint, adaptedBoard) -> {
                omokBoard.forbidden(position)
                ForbiddenMove
            }

            else -> Playing
        }
    }

    private fun isForbidden(
        adaptedPoint: Pair<Int, Int>,
        adaptedBoard: List<List<Int>>,
    ): Boolean = FourFourRule.validate(adaptedBoard, adaptedPoint) || ThreeThreeRule.validate(adaptedBoard, adaptedPoint)
}
