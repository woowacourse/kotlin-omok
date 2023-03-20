package domain.state

import domain.stone.Stone
import domain.stone.StoneType

interface State {

    fun next(stone: Stone): State

    fun getWinner(): StoneType
}
