package domain.state

import domain.stone.Stone
import domain.stone.StoneType
import rule.BlackRenjuRule
import rule.WhiteRenjuRule

interface State {
    val blackRenjuRule: BlackRenjuRule
        get() = BlackRenjuRule(15, 15)

    val whiteRenjuRule: WhiteRenjuRule
        get() = WhiteRenjuRule(15, 15)

    fun put(stone: Stone): State

    fun getWinner(): StoneType
}
