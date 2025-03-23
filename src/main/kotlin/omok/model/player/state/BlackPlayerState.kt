package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.rule.FourFourRule
import omok.model.rule.OmokAdapter
import omok.model.rule.ThreeThreeRule
import omok.model.rule.WinRule

class BlackPlayerState : PlayerState {
    override fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
    ): PlayerState {
        omokBoard.placeStone(position, this)
        val adaptedBoard = OmokAdapter.adaptOmokBoard(omokBoard)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(position)
        return when {
            WinRule.validate(adaptedBoard, adaptedPoint) -> Win()
            isForbidden(adaptedPoint, adaptedBoard) -> {
                omokBoard.forbidden(position)
                this
            }

            else -> WhitePlayerState()
        }
    }

    private fun isForbidden(
        adaptedPoint: Pair<Int, Int>,
        adaptedBoard: List<List<Int>>,
    ): Boolean = FourFourRule.validate(adaptedBoard, adaptedPoint) || ThreeThreeRule.validate(adaptedBoard, adaptedPoint)
}
