package domain

class Stones(val values: List<Stone>) {

    init {
        checkHasDuplicatePosition()
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

    private fun checkHasDuplicatePosition() {
        check(values.all { stone -> getCountSamePositionStone(stone) == ONLY_ONE_POSITION }) { DUPLICATE_POSITION_ERROR }
    }

    private fun getCountSamePositionStone(stone: Stone): Int {
        return values.count { it.position == stone.position }
    }

    companion object {
        private const val DUPLICATE_POSITION_ERROR = "같은 위치에 있는 돌이 존재할 수 없어요!"
        private const val ONLY_ONE_POSITION = 1
    }
}
