package omok.domain

import omok.domain.player.Black
import omok.domain.player.Player
import omok.domain.player.White

class Turn(val now: Player) {

    fun next(): Turn {
        if (now == Black) return Turn(White)
        return Turn(Black)
    }
}
