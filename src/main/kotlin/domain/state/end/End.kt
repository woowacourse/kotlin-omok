package domain.state.end

import domain.state.State
import domain.stone.StonePosition
import domain.stone.StoneType

class End(val stoneType: StoneType) : State {

    override fun getWinner(): StoneType = stoneType

    override fun next(stonePosition: StonePosition): State {
        TODO("Not yet implemented")
    }
}
