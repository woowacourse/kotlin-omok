package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.rule.OmokAdapter
import omok.model.rule.WinRule
import omok.model.stone.PositionState

class WhitePlayerState : PlayerState {
    override fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
        positionState: PositionState,
    ): PlayerState {
        omokBoard.placeStone(position, positionState)
        val adaptedBoard = OmokAdapter.adaptOmokBoard(omokBoard)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(position)
        if (WinRule.validate(adaptedBoard, adaptedPoint)) return Win()

        return this
    }
}
