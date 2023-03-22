package adapter

import domain.stone.Board
import domain.stone.Stone
import rule.BlackRenjuRule
import rule.type.Violation

class BlackTurnAdapter(private val blackRenjuRule: BlackRenjuRule = BlackRenjuRule()) : Adapter() {

    fun checkAnyFoulCondition(board: Board, stone: Stone): Boolean {
        if (blackRenjuRule.checkAnyFoulCondition(
                getBlackStonesPoint(board),
                getWhiteStonesPoint(board),
                stone.position.getPoint(),
            ) != Violation.NONE
        ) {
            return true
        }
        return false
    }

    override fun checkWin(board: Board, stone: Stone): Boolean {
        return blackRenjuRule.checkWin(
            getBlackStonesPoint(board),
            getWhiteStonesPoint(board),
            stone.position.getPoint(),
        )
    }
}
