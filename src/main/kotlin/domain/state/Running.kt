package domain.state

import domain.Stone
import domain.XCoordinate
import domain.YCoordinate

abstract class Running(private val blackStones: Set<Stone>, private val whiteStones: Set<Stone>) : State {

    init {
        require(blackStones.completeOmok().not() && whiteStones.completeOmok().not()) { RUNNING_COMPLETE_ERROR }
        require(blackStones.intersect(whiteStones).isEmpty()) { BLACK_WHITE_INTERSECT_ERROR }
    }

    fun checkAlreadyPlaced(stone: Stone) {
        require(stone !in blackStones && stone !in whiteStones) { ALREADY_PLACED_ERROR }
    }

    protected fun Set<Stone>.completeOmok(): Boolean {
        val xCoordinateRange = XCoordinate.X_MIN_RANGE..XCoordinate.X_MAX_RANGE
        val yCoordinateRange = YCoordinate.Y_MIN_RANGE..YCoordinate.Y_MAX_RANGE
        for (x in xCoordinateRange) {
            if (fun2(this, x)) return true
        }
        for (y in yCoordinateRange) {
            if (fun1(this, y)) return true
        }
        // 왼쪽 위에서 시작하는 대각선
        for ((maxX, maxY) in xCoordinateRange.zip(yCoordinateRange)) {
            var linkedCount = 0
            for ((x, y) in (XCoordinate.X_MIN_RANGE..maxX).zip(maxY downTo YCoordinate.Y_MIN_RANGE)) {
                val stone = Stone(XCoordinate.of(x), YCoordinate.of(y))
                if (stone in this) linkedCount++ else linkedCount = 0
                if (linkedCount >= 5) return true
            }
        }
        for ((minX, minY) in xCoordinateRange.zip(yCoordinateRange)) {
            var linkedCount = 0
            for ((x, y) in (minX..XCoordinate.X_MAX_RANGE).zip(YCoordinate.Y_MAX_RANGE downTo minY).toList()) {
                val stone = Stone(XCoordinate.of(x), YCoordinate.of(y))
                if (stone in this) linkedCount++ else linkedCount = 0
                if (linkedCount >= 5) return true
            }
        }

        // 왼쪽 아래에서 시작하는 대각선
        for ((maxX, minY) in xCoordinateRange.zip(yCoordinateRange.reversed())) {
            var linkedCount = 0
            for ((x, y) in (XCoordinate.X_MIN_RANGE..maxX).zip(minY..YCoordinate.Y_MAX_RANGE)) {
                val stone = Stone(XCoordinate.of(x), YCoordinate.of(y))
                if (stone in this) linkedCount++ else linkedCount = 0
                if (linkedCount >= 5) return true
            }
        }
        for ((minX, maxY) in xCoordinateRange.zip(yCoordinateRange.reversed())) {
            var linkedCount = 0
            for ((x, y) in (minX..XCoordinate.X_MAX_RANGE).zip(YCoordinate.Y_MIN_RANGE..maxY)) {
                val stone = Stone(XCoordinate.of(x), YCoordinate.of(y))
                if (stone in this) linkedCount++ else linkedCount = 0
                if (linkedCount >= 5) return true
            }
        }
        return false
    }

    private fun fun1(placedStones: Set<Stone>, y: Int): Boolean {
        var linkedCount = 0
        for (x in XCoordinate.X_MIN_RANGE..XCoordinate.X_MAX_RANGE) {
            val stone = Stone(XCoordinate.of(x), YCoordinate.of(y))
            if (stone in placedStones) linkedCount++ else linkedCount = 0
            if (linkedCount >= 5) return true
        }
        return false
    }

    private fun fun2(placedStones: Set<Stone>, x: Char): Boolean {
        var linkedCount = 0
        for (y in YCoordinate.Y_MIN_RANGE..YCoordinate.Y_MAX_RANGE) {
            val stone = Stone(XCoordinate.of(x), YCoordinate.of(y))
            if (stone in placedStones) linkedCount++ else linkedCount = 0
            if (linkedCount >= 5) return true
        }
        return false
    }

    companion object {
        private const val RUNNING_COMPLETE_ERROR = "게임이 진행되는 상태에서는 오목이 완성될 수 없습니다."
        private const val BLACK_WHITE_INTERSECT_ERROR = "흑돌과 백돌이 겹칠 수 없습니다."
        private const val ALREADY_PLACED_ERROR = "이미 놓여진 곳에 둘 수 없습니다."
    }
}
