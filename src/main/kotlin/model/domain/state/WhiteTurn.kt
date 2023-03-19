package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone
import model.domain.tools.Stone.WHITE

class WhiteTurn(board: Board) : Turn(board) {
    override val stone: Stone = WHITE
    override fun place(location: Location): State {
        board.placeStone(location, stone)
        if (isOmok(location)) return WhiteOmok(board)
        return BlackTurn(board)
    }

    override fun isForbidden(location: Location) = false
}
