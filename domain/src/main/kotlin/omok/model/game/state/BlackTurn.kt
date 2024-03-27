package omok.model.game.state

import omok.model.Position
import omok.model.StoneColor
import omok.model.board.Board
import omok.model.rule.RenjuRule

class BlackTurn(board: Board) : Running(RenjuRule, board) {
    override fun placeStone(onPlace: () -> Position): GameState {
        return super.placeStone(StoneColor.BLACK, ::WhiteTurn, onPlace)
    }
}
