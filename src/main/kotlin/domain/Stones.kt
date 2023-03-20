package domain.domain

class Stones(values: List<Stone>) {
    private val _values = values.deepCopy()
    val values: List<Stone>
        get() = _values.deepCopy()

    private fun List<Stone>.deepCopy(): List<Stone> = map { it.copy() }
    fun addStone(stone: Stone): Stones {
        val newStones = values.toMutableList()
        newStones.add(stone)
        return Stones(newStones)
    }

    fun isContainSamePositionStone(position: Position2): Boolean {
        return values.any { it.position == position }
    }

    fun getBlackStonesCount(): Int {
        if (values.isEmpty()) return 0
        return values.count { it.isBlack() }
    }

    fun getWhiteStonesCount(): Int {
        if (values.isEmpty()) return 0
        return values.count { it.isWhite() }
    }
}
