package domain.judgement

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

interface ForbiddenCondition {
    fun isForbidden(board: Map<Position, Color?>, newStone: Stone): Boolean
}
