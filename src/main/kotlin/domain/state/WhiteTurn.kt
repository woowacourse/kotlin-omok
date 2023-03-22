package domain.state

import domain.stone.Board
import domain.stone.Stone

class WhiteTurn(val board: Board) : Running() {
    override fun put(stone: Stone): State {
        runCatching { board.putStone(stone) }.onFailure { return WhiteTurn(board) }
        if (whiteRenjuRule.checkWin(board.whiteStonesPoint(), board.blackStonesPoint(), stone.point)) {
            return End(stone)
        }
        return BlackTurn(board)
    }
}
