package domain.state

import domain.stone.StonePosition
import domain.stone.StoneType

interface State {

    fun next(stonePosition: StonePosition): State

    fun getWinner(): StoneType
}
