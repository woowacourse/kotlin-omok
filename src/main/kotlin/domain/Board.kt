package domain

import domain.rule.BlackRuleAdapter
import domain.state.BlackTurn
import domain.state.BlackWin
import domain.state.Finished
import domain.state.State

class Board {

    private var state: State = BlackTurn(setOf(), setOf())

    fun put(stone: Stone) {
        //추후 백돌 규칙 생기면 WhiteRuleAdapter 추가 가능
        state = state.put(stone, BlackRuleAdapter())
    }

    fun isFinished(): Boolean = state is Finished

    fun isBlackTurn(): Boolean = state is BlackTurn

    fun isBlackWin(): Boolean = state is BlackWin

    fun blackStoneIsPlaced(stone: Stone): Boolean = state.blackStones.contains(stone)

    fun whiteStoneIsPlaced(stone: Stone): Boolean = state.whiteStones.contains(stone)
}
