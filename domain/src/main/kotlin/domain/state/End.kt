package domain.state

import domain.stone.Stone
import domain.stone.StoneType

class End(private val stone: Stone) : State {

    override val stoneColor: StoneType
        get() = StoneType.EMPTY
    override fun put(stone: Stone): State = this

    fun getWinner(): StoneType = stone.type
}
