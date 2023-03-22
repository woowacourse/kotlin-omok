package listener

import domain.point.Point
import domain.stone.StoneColor

interface OmokTurnEventListener {
    fun onTakeTurn(stoneColor: StoneColor): Point
    fun onNotPlaceable()
}
