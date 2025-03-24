package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.rule.BlackWinRule
import omok.model.rule.FourFourRule
import omok.model.rule.OmokAdapter
import omok.model.rule.ThreeThreeRule
import omok.model.stone.StoneColor

class BlackPlayerState : PlayerState {
    override fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
    ): GameState {
        omokBoard.placeStone(position, StoneColor.BLACK)
        val adaptedBoard = OmokAdapter.adaptOmokBoard(omokBoard)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(position)
        return when {
            BlackWinRule.validate(adaptedBoard, adaptedPoint) -> GameState.Win
            isForbidden(adaptedPoint, adaptedBoard) -> {
                omokBoard.forbidden(position)
                GameState.ForbiddenMove
            }

            else -> GameState.Playing
        }
    }

    private fun isForbidden(
        adaptedPoint: Pair<Int, Int>,
        adaptedBoard: List<List<Int>>,
    ): Boolean = FourFourRule.validate(adaptedBoard, adaptedPoint) || ThreeThreeRule.validate(adaptedBoard, adaptedPoint)
}
