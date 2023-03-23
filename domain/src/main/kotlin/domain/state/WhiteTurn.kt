package domain.state

import adapter.WhiteRuleAdapter
import domain.stone.Board
import domain.stone.Stone

class WhiteTurn(val board: Board) : Running() {

    val whiteTurnAdapter = WhiteRuleAdapter()
    override fun put(stone: Stone): State {
        runCatching { board.putStone(stone) }.onFailure { return WhiteTurn(board) }
        if (whiteTurnAdapter.checkWin(board, stone)) return End(stone)
        return BlackTurn(board)
    }
}
