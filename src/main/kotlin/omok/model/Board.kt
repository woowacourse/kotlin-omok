package omok.model

import RenjuRule

class Board(val stones: Stones) {
    private val rule: Rule = RenjuRule(stones)

    fun putStone(stone: Stone): Boolean {
        if (rule.checkInvalid(stone)) {
            return false
        }
        return stones.putStone(stone)
    }
}
