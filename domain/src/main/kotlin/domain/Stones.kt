package domain

class Stones(val values: List<Stone>) {

    init {
        check(values.all { stone -> values.count { it.position == stone.position } == ONLY_ONE_POSITION }) { DUPLICATE_POSITION_ERROR }
    }

    fun addStone(stone: Stone): Stones {
        return Stones(values.plus(stone))
    }

    fun isContainSamePositionStone(position: Position): Boolean {
        return values.any { it.position == position }
    }

    fun getLastStone(): Stone? {
        return values.lastOrNull()
    }

    companion object {
        private const val DUPLICATE_POSITION_ERROR = "같은 위치에 있는 돌이 존재할 수 없어요!"
        private const val ONLY_ONE_POSITION = 1
    }
}
