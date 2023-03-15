package domain

import domain.state.BlackTurn
import domain.state.BlackWin
import domain.state.Finished
import domain.state.State

class Board {

    private var state: State = BlackTurn(setOf(), setOf())
    val blackStones
        get() = state.blackStones
    val whiteStones
        get() = state.whiteStones

    fun put(stone: Stone) {
        state = state.put(stone)
    }

    fun isFinished(): Boolean = state is Finished

    fun isBlackTurn(): Boolean = state is BlackTurn

    fun isBlackWin(): Boolean = state is BlackWin
}