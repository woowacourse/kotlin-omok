package omok.model

class Stones {
    private val _stones = mutableListOf<Stone>()
    val stones: List<Stone>
        get() = _stones.toList()

    private val rule: Rule
        get() = RenjuRuleAdapter()

    fun add(stone: Stone) {
        require(!rule.isInValid(_stones, stone)) { "렌주룰을 어겼습니다." }
        checkDuplicate(stone)

        _stones.add(stone)
    }

    private fun checkDuplicate(stone: Stone) {
        require(
            _stones.all { it.point != stone.point },
        ) { "중복된 위치입니다." }
    }

    fun lastStone(): Stone? = _stones.lastOrNull()

    fun clear() = _stones.clear()

    companion object {
        private const val BOARD_SIZE = 15

        fun getSize(): Int = BOARD_SIZE
    }
}
