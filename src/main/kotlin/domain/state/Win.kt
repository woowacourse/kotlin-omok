package domain.state

import domain.stone.Stone
import domain.stone.StoneType

class Win(private val stone: Stone) : End() {
    override fun getWinner(): StoneType = stone.type
}
