package domain.judgement

import domain.stone.Stone

interface ForbiddenPositionChecker {
    fun isForbidden(placedStones: List<Stone>, newStone: Stone): Boolean
}
