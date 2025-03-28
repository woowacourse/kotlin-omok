package woowacourse.omok.domain.stone

class Stones(
    private val _value: MutableList<Stone> = mutableListOf(),
) {
    val value get() = _value.toList()

    fun add(stone: Stone) {
        _value.add(stone)
    }

    fun lastStone(): Stone? = value.lastOrNull()

    fun playerStones(stoneColor: StoneColor): Stones = Stones(value.filter { stone -> stone.color == stoneColor }.toMutableList())

    fun otherStones(stoneColor: StoneColor): Stones = Stones(value.filter { stone -> stone.color != stoneColor }.toMutableList())
}
