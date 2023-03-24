package domain

class Stones(value: List<Stone> = listOf()) {
    private val _value: MutableList<Stone> = value.toMutableList()

    val value: List<Stone>
        get() = _value.toList()
    val renjuRuleAdapter = RenjuRuleAdapter(this)

    fun place(stone: Stone) {
        require(value.none { it.coordinate == stone.coordinate }) {
            MESSAGE_CORRUPT_STONE.format(stone.coordinate.x, stone.coordinate.y)
        }
        _value.add(stone)
    }

    fun validateRenju(stone: Stone): Boolean {
        return when (stone.color) {
            Color.BLACK -> !renjuRuleAdapter.isThreeToThree(stone) && !renjuRuleAdapter.isFourToFour(
                stone
            ) && renjuRuleAdapter.findScore(
                stone
            ) < LARGE_PLACE

            Color.WHITE -> true
        }
    }

    fun isWinPlace(): Boolean {
        return renjuRuleAdapter.findScore(getLastStone()) >= WINNING_CONDITION
    }

    fun getLastStone(): Stone {
        return requireNotNull(this.value.lastOrNull()) { ERROR_STONES_EMPTY }
    }

    companion object {
        private const val MESSAGE_CORRUPT_STONE = "같은 위치에 이미 돌이 있습니다. 위치 : (%d, %d)"
        private const val WINNING_CONDITION = 4
        const val LARGE_PLACE = 5
        private const val ERROR_STONES_EMPTY = "놓여진 바둑돌이 없습니다"
    }
}
