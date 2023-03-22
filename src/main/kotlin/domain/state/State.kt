package domain.state

import domain.stone.Stone
import domain.stone.StoneType
import rule.BlackRenjuRule
import rule.WhiteRenjuRule

interface State {
    val blackRenjuRule: BlackRenjuRule
        get() = BlackRenjuRule(BOARD_SIZE, BOARD_SIZE)

    val whiteRenjuRule: WhiteRenjuRule
        get() = WhiteRenjuRule(BOARD_SIZE, BOARD_SIZE)

    fun put(stone: Stone): State

    fun getWinner(): StoneType

    companion object {
        private const val BOARD_SIZE = 15
    }
}
