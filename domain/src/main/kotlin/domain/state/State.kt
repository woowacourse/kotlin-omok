package domain.state

import domain.stone.Stone
import domain.stone.StoneType

interface State {

    val stoneColor: StoneType
    fun put(stone: Stone): State
}
