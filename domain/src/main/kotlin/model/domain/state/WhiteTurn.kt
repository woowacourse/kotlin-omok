package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Stone
import model.domain.tools.Stone.WHITE

class WhiteTurn(board: Board) : Turn(board) {
    override val stone: Stone = WHITE
}
