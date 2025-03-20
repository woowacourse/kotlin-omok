package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.rule.BlackWinRule
import omok.model.rule.FourFourRule
import omok.model.rule.OmokAdapter
import omok.model.rule.ThreeThreeRule
import omok.model.stone.StoneState

class BlackPlayerState(
    private val count: Int = 0,
) : PlayerState {
    override fun isPlaceTurn(
        omokBoard: OmokBoard,
        position: Position,
        stoneState: StoneState,
    ): PlayerState {
        omokBoard.placeStone(position, stoneState)
        val adaptedBoard = OmokAdapter.adaptOmokBoard(omokBoard)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(position)
        return when {
            BlackWinRule.validate(adaptedBoard, adaptedPoint) -> Win()
            FourFourRule.validate(adaptedBoard, adaptedPoint) -> {
                omokBoard.isDoubleFour(position)
                this
            }

            ThreeThreeRule.validate(adaptedBoard, adaptedPoint) -> {
                omokBoard.isDoubleThree(position)
                this
            }

            else -> BlackPlayerState(count + 1)
        }
    }

    override fun nextTurn(): PlayerState = WhitePlayerState()
}
