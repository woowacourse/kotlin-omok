package domain.turn

import domain.stone.Color
import domain.stone.Position

interface Turn {
    val curColor: Color
    val isFinished: Boolean
    fun addStone(position: Position): Turn
    fun getBoard(): Map<Position, Color?>
}
