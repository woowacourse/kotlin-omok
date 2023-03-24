package listener

import domain.position.Position
import domain.stone.StoneColor

interface OmokTurnEventListener {
    fun onTakeTurn(stoneColor: StoneColor): Position
    fun onNotPlaceable()
}
