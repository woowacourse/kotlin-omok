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
            FourFourRule.validate(adaptedBoard, adaptedPoint) -> {
                omokBoard.doubleFour(position)
                this
            }

            ThreeThreeRule.validate(adaptedBoard, adaptedPoint) -> {
                omokBoard.doubleThree(position)
                this
            }

            else -> this
        }
    }
}
