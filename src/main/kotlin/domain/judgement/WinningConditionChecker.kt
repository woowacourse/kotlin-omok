package domain.judgement

import domain.stone.Stone

interface WinningConditionChecker {
    fun isWin(placedStones: List<Stone>, newStone: Stone): Boolean
}
