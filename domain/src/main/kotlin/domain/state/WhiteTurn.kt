package domain.state

import adapter.WhiteRuleAdapter
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StoneType

class WhiteTurn(val board: Board) : Running() {

    val whiteTurnAdapter = WhiteRuleAdapter()
    override val stoneColor: StoneType
        get() = StoneType.WHITE

    override fun put(stone: Stone): State {
        runCatching { board.putStone(stone) }.onFailure { return WhiteTurn(board) }
        if (whiteTurnAdapter.checkWin(board, stone)) return End(stone)
        return BlackTurn(board)
    }
}
