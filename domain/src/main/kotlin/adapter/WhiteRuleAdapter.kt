package adapter

import domain.stone.Board
import domain.stone.Stone
import rule.WhiteRenjuRule

class WhiteRuleAdapter(private val whiteRenjuRule: WhiteRenjuRule = WhiteRenjuRule()) : OmokRuleAdapter() {
    override fun checkWin(board: Board, stone: Stone): Boolean {
        return whiteRenjuRule.checkWin(
            getWhiteStonesPoint(board),
            getBlackStonesPoint(board),
            stone.position.getPoint(),
        )
    }
}
