package omok.model.game.state

import omok.model.OmokStone
import omok.model.Position
import omok.model.StoneColor
import omok.model.board.Board
import omok.model.rule.OmokGameRule

class WhiteTurn(board: Board) : Running(whiteStoneRule, board) {
    override fun placeStone(position: Position): GameState {
        return super.placeStone(StoneColor.WHITE, position, ::BlackTurn)
    }

    override fun canPlaceStone(position: Position): Boolean {
        return whiteStoneRule.canPlaceStone(OmokStone(position, StoneColor.WHITE), board)
    }

    companion object {
        private val whiteStoneRule: OmokGameRule =
            OmokGameRule { stone, board -> board.canPlace(stone) }
    }
}
