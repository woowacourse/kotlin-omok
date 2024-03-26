package omok.model

class Board(val stones: Stones) {
    private val rule: Rule
        get() = RuleAdapter(stones)
    private val rule = RuleAdapter(BOARD_SIZE, ::getCurrentStones)

    fun putStone(stone: Stone) {
        if (!rule.checkInvalid(stone)) {
            stones.putStone(stone)
        } else {
            throw IllegalStateException(ERROR_FORBIDDEN_STONE)
        }
    }

    private fun getCurrentStones(): Stones {
        return stones
    }

    companion object {
        const val ERROR_FORBIDDEN_STONE = "금지된 수입니다."
    }
}
