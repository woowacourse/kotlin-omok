package omok.model

import RuleAdapter

class Board(val stones: Stones) {
    private val rule: Rule
        get() = RuleAdapter(stones)

    fun putStone(stone: Stone) {
        if (!rule.checkInvalid(stone)) {
            stones.putStone(stone)
        } else {
            throw IllegalStateException(ERROR_FORBIDDEN_STONE)
        }
    }

    companion object {
        const val ERROR_FORBIDDEN_STONE = "금지된 수입니다."
    }
}
