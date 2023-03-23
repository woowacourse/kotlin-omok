package domain.state

import adapter.BlackRuleAdapter
import domain.stone.Board
import domain.stone.Stone

class BlackTurn(val board: Board) : Running() {

    val blackTurnAdapter = BlackRuleAdapter()
    override fun put(stone: Stone): State {
        if (blackTurnAdapter.checkAnyFoulCondition(board, stone)) return BlackTurn(board)
        runCatching { board.putStone(stone) }.onFailure { return BlackTurn(board) }
        if (blackTurnAdapter.checkWin(board, stone)) return End(stone)
        return WhiteTurn(board)
    }
}
