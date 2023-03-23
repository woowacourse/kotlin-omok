package domain.state

import domain.Board.Companion.BOARD_SIZE
import domain.stone.Stone
import domain.stone.Stones

abstract class Running(stones: Stones) : State {

    init {
        require(
            stones.blackStones.completeOmok().not() && stones.whiteStones.completeOmok().not()
        ) { RUNNING_COMPLETE_ERROR }
        require(
            stones.blackStones.intersect(stones.whiteStones).isEmpty()
        ) { BLACK_WHITE_INTERSECT_ERROR }
    }

    fun checkAlreadyPlaced(point: Pair<Int, Int>) {
        require(point !in stones.stones.map { it.x to it.y }) { ALREADY_PLACED_ERROR }
    }

    protected fun Set<Stone>.completeOmok(): Boolean {
        val xRange = 0 until BOARD_SIZE
        val yRange = 0 until BOARD_SIZE
        for (x in xRange) {
            if (verticalCompleteCheck(this, x)) return true
        }
        for (y in yRange) {
            if (horizontalCompleteCheck(this, y)) return true
        }
        if (upperLeftDiagonal(xRange, yRange, this)) return true
        if (upperRightDiagonal(xRange, yRange, this)) return true
        return false
    }

    private fun horizontalCompleteCheck(placedStones: Set<Stone>, y: Int): Boolean {
        var linkedCount = 0
        for (x in 0 until BOARD_SIZE) {
            if (x to y in placedStones.map { it.x to it.y }) linkedCount++ else linkedCount = 0
            if (linkedCount >= 5) return true
        }
        return false
    }

    private fun verticalCompleteCheck(placedStones: Set<Stone>, x: Int): Boolean {
        var linkedCount = 0
        for (y in 0 until BOARD_SIZE) {
            if (x to y in placedStones.map { it.x to it.y }) linkedCount++ else linkedCount = 0
            if (linkedCount >= 5) return true
        }
        return false
    }

    private fun upperLeftDiagonal(
        xCoordinateRange: IntRange,
        yCoordinateRange: IntRange,
        stones: Set<Stone>
    ): Boolean {
        // 왼쪽 위에서 시작하는 대각선
        for ((maxX, maxY) in xCoordinateRange.zip(yCoordinateRange)) {
            var linkedCount = 0
            for ((x, y) in (0..maxX).zip(maxY downTo 0)) {
                if (x to y in stones.map { it.x to it.y }) linkedCount++ else linkedCount = 0
                if (linkedCount >= 5) return true
            }
        }
        for ((minX, minY) in xCoordinateRange.zip(yCoordinateRange)) {
            var linkedCount = 0
            for ((x, y) in (minX until BOARD_SIZE).zip(BOARD_SIZE - 1 downTo minY).toList()) {
                if (x to y in stones.map { it.x to it.y }) linkedCount++ else linkedCount = 0
                if (linkedCount >= 5) return true
            }
        }
        return false
    }

    private fun upperRightDiagonal(
        xCoordinateRange: IntRange,
        yCoordinateRange: IntRange,
        stones: Set<Stone>
    ): Boolean {
        // 왼쪽 아래에서 시작하는 대각선
        for ((maxX, minY) in xCoordinateRange.zip(yCoordinateRange.reversed())) {
            var linkedCount = 0
            for ((x, y) in (0..maxX).zip(minY until BOARD_SIZE)) {
                if (x to y in stones.map { it.x to it.y }) linkedCount++ else linkedCount = 0
                if (linkedCount >= 5) return true
            }
        }
        for ((minX, maxY) in xCoordinateRange.zip(yCoordinateRange.reversed())) {
            var linkedCount = 0
            for ((x, y) in (minX until BOARD_SIZE).zip(0..maxY)) {
                if (x to y in stones.map { it.x to it.y }) linkedCount++ else linkedCount = 0
                if (linkedCount >= 5) return true
            }
        }
        return false
    }

    companion object {
        private const val RUNNING_COMPLETE_ERROR = "게임이 진행되는 상태에서는 오목이 완성될 수 없습니다."
        private const val BLACK_WHITE_INTERSECT_ERROR = "흑돌과 백돌이 겹칠 수 없습니다."
        private const val ALREADY_PLACED_ERROR = "이미 놓여진 곳에 둘 수 없습니다."
    }
}
