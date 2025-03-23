package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.rule.FourFourRule
import omok.model.rule.OmokAdapter
import omok.model.rule.ThreeThreeRule
import omok.model.rule.WinRule
import omok.model.stone.PositionState

class BlackPlayerState : PlayerState {
    override fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
        positionState: PositionState,
    ): PlayerState {
        omokBoard.placeStone(position, positionState)
        val adaptedBoard = OmokAdapter.adaptOmokBoard(omokBoard)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(position)
        return when {
            WinRule.validate(adaptedBoard, adaptedPoint) -> Win()
            isForbidden(position, omokBoard) -> {
                omokBoard.forbidden(position)
                this
            }

            else -> this
        }
    }

    private fun isForbidden(
        position: Position,
        omokBoard: OmokBoard,
    ): Boolean {
        val adaptedBoard = OmokAdapter.adaptOmokBoard(omokBoard)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(position)
        return FourFourRule.validate(adaptedBoard, adaptedPoint) || ThreeThreeRule.validate(adaptedBoard, adaptedPoint)
    }
}
