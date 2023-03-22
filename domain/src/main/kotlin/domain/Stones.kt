package domain

class Stones(val values: List<Stone>) {

    fun addStone(stone: Stone): Stones {
        val newList = values.plus(stone)
        return Stones(newList)
    }

    fun isContainSamePositionStone(position: Position): Boolean {
        return values.any { it.position == position }
    }

    fun getLastStone(): Stone? {
        return values.lastOrNull()
    }
}
