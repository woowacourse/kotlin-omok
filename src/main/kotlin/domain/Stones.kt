package domain

class Stones(value: List<Stone> = listOf()) {
    private val _value: MutableList<Stone> = value.toMutableList()
    val renjuRule = RenjuRule(this)
    val value: List<Stone>
        get() = _value.toList()

    fun place(stone: Stone) {
        require(value.none { it.coordinate == stone.coordinate }) {
            MESSAGE_CORRUPT_STONE.format(stone.coordinate.x, stone.coordinate.y)
        }
        _value.add(stone)
    }

    fun validateRenju(stone: Stone): Boolean {
        return when (stone.color) {
            Color.BLACK -> !this.renjuRule.isThreeToThree(stone) && !this.renjuRule.isFourToFour(stone) && this.renjuRule.findScore(
                stone
            ) < LARGE_PLACE

            Color.WHITE -> true
        }
    }

    fun isWinPlace(): Boolean {
        validateEmptyStones()
        return renjuRule.findScore(this.value.last()) >= WINNING_CONDITION
    }

    fun validateEmptyStones() {
        require(this.value.isNotEmpty()) { ERROR_STONES_EMPTY }
    }

    companion object {
        private const val MESSAGE_CORRUPT_STONE = "같은 위치에 이미 돌이 있습니다. 위치 : (%d, %d)"
        private const val WINNING_CONDITION = 4
        const val LARGE_PLACE = 5
        private const val ERROR_STONES_EMPTY = "놓여진 바둑돌이 없습니다"
    }
}
