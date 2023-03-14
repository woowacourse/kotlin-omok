package domain.state

import domain.stone.StoneType

interface State {

    fun isValidPut(): Boolean

    fun put(): State

    fun getWinner(): StoneType
}
