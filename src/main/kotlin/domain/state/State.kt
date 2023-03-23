package domain.state

import domain.stone.Stone

interface State {

    val blackStones: Set<Stone>
    val whiteStones: Set<Stone>

    fun put(stone: Stone): State

    fun canPut(nextStone: Stone): Boolean
}
