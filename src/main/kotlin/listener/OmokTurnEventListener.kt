package listener

import domain.Position
import domain.StoneColor

interface OmokTurnEventListener {
    fun onTakeTurn(stoneColor: StoneColor): Position
    fun onNotPlaceable()
}
