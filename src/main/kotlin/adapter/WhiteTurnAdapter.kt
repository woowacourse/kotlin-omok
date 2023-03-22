package adapter

import domain.stone.Board
import domain.stone.Stone
import rule.WhiteRenjuRule

class WhiteTurnAdapter(private val whiteRenjuRule: WhiteRenjuRule = WhiteRenjuRule()) : Adapter() {
    override fun checkWin(board: Board, stone: Stone): Boolean {
        return whiteRenjuRule.checkWin(
            getWhiteStonesPoint(board),
            getBlackStonesPoint(board),
            stone.position.getPoint(),
        )
    }
}
