package domain.stone

class Stones(values: List<Stone> = emptyList()) {
    private val _values = values.toMutableList()
    val values: List<Stone>
        get() = _values.toList()

    constructor(vararg stones: Stone) : this(stones.toList())

    fun add(stone: Stone) {
        _values.add(stone)
    }

    fun containsPosition(stone: Stone): Boolean =
        values.asSequence().map { it.position }.contains(stone.position)

    fun matrixBoard(): List<List<Stone?>> {
        val board: MutableList<MutableList<Stone?>> = MutableList(16) { MutableList(15) { null } }

        _values.forEach { board[(it.position.y)][it.position.x] = it }

        return board
    }

    operator fun plus(stones: Stones): Stones = Stones(values.plus(stones.values))
}
