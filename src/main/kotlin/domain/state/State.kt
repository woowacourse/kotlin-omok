package domain.state

import domain.stone.Stone
import domain.stone.StoneType

interface State {

    fun put(stone: Stone): State

    fun getWinner(): StoneType

    fun isValidPut(stone: Stone): Boolean

    fun isOmokCondition(stone: Stone): Boolean
}
