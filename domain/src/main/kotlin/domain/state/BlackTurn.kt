package domain.state

import adapter.BlackRuleAdapter
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StoneType

class BlackTurn(val board: Board) : Running() {

    val blackTurnAdapter = BlackRuleAdapter()
    override val stoneColor: StoneType
        get() = StoneType.BLACK
    override fun put(stone: Stone): State {
        if (blackTurnAdapter.checkAnyFoulCondition(board, stone)) return BlackTurn(board)
        runCatching { board.putStone(stone) }.onFailure { return BlackTurn(board) }
        if (blackTurnAdapter.checkWin(board, stone)) return End(stone)
        return WhiteTurn(board)
    }
}
