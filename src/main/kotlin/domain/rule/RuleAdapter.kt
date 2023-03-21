package domain.rule

import domain.Stone

abstract class RuleAdapter {
    abstract fun checkStone(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone)
}