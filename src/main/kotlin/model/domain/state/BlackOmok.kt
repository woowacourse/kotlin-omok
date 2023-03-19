package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Stone

class BlackOmok(board: Board) : End(board) {
    override val stone: Stone = Stone.BLACK
}
