package omok.model

class Board {
    private val _stones = mutableListOf<Stone>()
    val stones: List<Stone>
        get() = _stones.toList()

    private val rule: Rule
        get() = RenjuRuleAdapter()

    fun add(stone: Stone) {
        require(!rule.isInValid(stones, stone)) { "렌주룰을 어겼습니다." }
        checkDuplicate(stone)

        _stones.add(stone)
    }

    private fun checkDuplicate(stone: Stone) {
        require(
            stones.all { it.point != stone.point }
        ) { "중복된 위치입니다." }
    }

    fun lastStone(): Stone? = stones.lastOrNull()

    companion object {
        private const val BOARD_SIZE = 15

        fun getSize(): Int = BOARD_SIZE
    }
}
