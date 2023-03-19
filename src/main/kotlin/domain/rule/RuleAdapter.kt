package domain.rule

import domain.Stone

interface RuleAdapter {
    fun checkPutStone(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone)
}