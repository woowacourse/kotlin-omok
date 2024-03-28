package omok.model.game.state

import omok.model.OmokStone
import omok.model.Position
import omok.model.StoneColor
import omok.model.board.Board
import omok.model.rule.OmokGameRule
import omok.model.rule.RenjuRule

class BlackTurn(board: Board) : Running(blackStoneRule, board) {
    override fun placeStone(position: Position): GameState {
        return super.placeStone(StoneColor.BLACK, position, ::WhiteTurn)
    }

    override fun canPlaceStone(position: Position): Boolean {
        return blackStoneRule.canPlaceStone(OmokStone(position, StoneColor.BLACK), board)
    }

    companion object {
        private val blackStoneRule: OmokGameRule = RenjuRule
    }
}
