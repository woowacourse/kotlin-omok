package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.rule.FourFourRule
import omok.model.rule.OmokAdapter
import omok.model.rule.ThreeThreeRule
import omok.model.rule.WinRule
import omok.model.stone.StoneState

class BlackPlayerState : PlayerState {
    override fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
        stoneState: StoneState,
    ): PlayerState {
        omokBoard.placeStone(position, stoneState)
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
