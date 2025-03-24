package omok.model.player.state

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.rule.OmokAdapter
import omok.model.rule.WhiteWinRule
import omok.model.stone.StoneColor

class WhitePlayerState : PlayerState {
    override fun placeTurn(
        omokBoard: OmokBoard,
        position: Position,
    ): GameState {
        omokBoard.placeStone(position, StoneColor.WHITE)
        val adaptedBoard = OmokAdapter.adaptOmokBoard(omokBoard)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(position)
        if (WhiteWinRule.validate(adaptedBoard, adaptedPoint)) return GameState.Win

        return GameState.Playing
    }
}
