package model.domain.state

import model.domain.rule.OmokForbiddenRule
import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone
import model.domain.tools.Stone.BLACK

class BlackTurn(board: Board) : Turn(board) {
    override val stone: Stone = BLACK
    override fun place(location: Location): State {
        if (isForbidden(location)) return BlackTurn(board)
        board.placeStone(location, stone)
        if (isOmok(location)) return BlackOmok(board)
        return WhiteTurn(board)
    }

    override fun isForbidden(location: Location) = OmokForbiddenRule(board, stone).isForbidden(location)
}
