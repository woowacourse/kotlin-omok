package domain.judgement

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

interface WinningCondition {
    fun isWin(placedStones: Map<Position, Color?>, newStone: Stone): Boolean
}
