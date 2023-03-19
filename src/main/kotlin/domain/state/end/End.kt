package domain.state.end

import domain.state.State
import domain.stone.Stone
import domain.stone.StoneType

class End(val stoneType: StoneType) : State {

    override fun getWinner(): StoneType = stoneType

    override fun isValidPut(stone: Stone): Boolean {
        TODO("Not yet implemented")
    }

    override fun put(stone: Stone): State {
        TODO("Not yet implemented")
    }
}
