package domain.state.end

import domain.state.State
import domain.stone.Stone
import domain.stone.StoneType

class End(val stoneType: StoneType) : State {

    override fun getWinner(): StoneType = stoneType

    override fun next(stone: Stone): State {
        TODO("Not yet implemented")
    }
}
