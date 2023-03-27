package domain.judgement

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

interface Rule {
    fun isForbidden(board: Map<Position, Color?>, newStone: Stone): Boolean
    fun isWin(board: Map<Position, Color?>, newStone: Stone): Boolean
}
