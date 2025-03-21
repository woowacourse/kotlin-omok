package omok.domain

import rule.wrapper.point.Point

class OmokGrid() {
    private val _whiteStones: MutableSet<Point> = mutableSetOf()
    val whiteStones: Set<Point>
        get() = _whiteStones.deepCopy()

    private val _blackStones: MutableSet<Point> = mutableSetOf()
    val blackStones: Set<Point>
        get() = _blackStones.deepCopy()

    fun validateEmptyPoint(point: Point) {
        val searchedPoint = (_whiteStones + _blackStones).find { it == point }
        check(searchedPoint == null) { ERROR_STONE_ALREADY_PUT }
    }

    fun putStone(
        point: Point,
        state: StoneState,
    ) {
        when (state) {
            StoneState.WHITE -> _whiteStones.add(point)
            StoneState.BLACK -> _blackStones.add(point)
            StoneState.BLANK -> throw IllegalStateException()
        }
    }

    fun isFull(): Boolean {
        return (_whiteStones + _blackStones).size == TOTAL_POINT_COUNT
    }

    companion object {
        const val DEFAULT_SIZE: Int = 15
        const val TOTAL_POINT_COUNT: Int = 225
        private const val ERROR_STONE_ALREADY_PUT = "이미 돌이 있습니다."
    }
}

fun MutableSet<Point>.deepCopy(): Set<Point> = map { it.copy() }.toSet()
