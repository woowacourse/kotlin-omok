package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.rule.OmokAdapter
import omok.model.rule.WhiteWinRule

class WhitePlayerState : PlayerState {
    override fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
    ): PlayerState {
        omokBoard.placeStone(position, this)
        val adaptedBoard = OmokAdapter.adaptOmokBoard(omokBoard)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(position)
        if (WhiteWinRule.validate(adaptedBoard, adaptedPoint)) return Win()

        return BlackPlayerState()
    }
}
