package omok.model.game.state

import omok.model.Position
import omok.model.StoneColor
import omok.model.board.Board
import omok.model.rule.OmokGameRule

class WhiteTurn(board: Board) : Running(whiteStoneRule, board) {
    override fun placeStone(onPlace: () -> Position): GameState {
        return super.placeStone(StoneColor.WHITE, ::BlackTurn, onPlace)
    }

    companion object {
        private val whiteStoneRule: OmokGameRule = OmokGameRule { stone, board -> board.canPlace(stone) }
    }
}
