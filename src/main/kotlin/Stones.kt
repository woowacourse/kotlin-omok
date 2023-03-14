

class Stones(list: List<Stone>) {
    private val _list = list.toList()
    val list: List<Stone>
        get() = _list.toList()

    constructor(vararg stone: Stone) : this(stone.toList())

    fun putStone(stone: Stone): Stones = Stones(list + stone)

    fun getLatestStone(): Point = _list.last().point
}
