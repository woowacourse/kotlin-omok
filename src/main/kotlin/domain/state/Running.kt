package domain.state

import domain.Board
import domain.Direction
import domain.Point
import domain.Stone

abstract class Running(blackStones: Set<Stone>, whiteStones: Set<Stone>) : State {

    init {
        require(blackStones.isCompletedOmok().not() && whiteStones.isCompletedOmok().not()) { RUNNING_COMPLETE_ERROR }
        require(blackStones.intersect(whiteStones).isEmpty()) { BLACK_WHITE_INTERSECT_ERROR }
    }

    fun checkAlreadyPlaced(stone: Stone) {
        require(stone !in blackStones && stone !in whiteStones) { ALREADY_PLACED_ERROR }
    }

    protected fun Set<Stone>.isCompletedOmok(): Boolean =
        this.isCompletedVerticalOmok() ||
            this.isCompletedHorizontalOmok() ||
            this.isCompletedUpperLeftDiagonalOmok() ||
            this.isCompletedUpperRightDiagonalOmok()

    private fun Set<Stone>.isCompletedVerticalOmok(): Boolean {
        for (x in 1..Board.SIZE) {
            if (this.isCompletedOmokOnThisLine(Point(x, 1), Direction.UP)) return true
        }
        return false
    }

    private fun Set<Stone>.isCompletedHorizontalOmok(): Boolean {
        for (y in 1..Board.SIZE) {
            if (this.isCompletedOmokOnThisLine(Point(1, y), Direction.RIGHT)) return true
        }
        return false
    }

    private fun Set<Stone>.isCompletedUpperLeftDiagonalOmok(): Boolean {
        val boardPointRange = 1..Board.SIZE
        for (x in boardPointRange) {
            if (this.isCompletedOmokOnThisLine(Point(x, 1), Direction.UP_LEFT)) return true
        }
        for (y in boardPointRange) {
            if (this.isCompletedOmokOnThisLine(Point(Board.SIZE, y), Direction.UP_LEFT)) return true
        }
        return false
    }

    private fun Set<Stone>.isCompletedUpperRightDiagonalOmok(): Boolean {
        val boardPointRange = 1..Board.SIZE
        for (y in boardPointRange.reversed()) {
            if (this.isCompletedOmokOnThisLine(Point(1, y), Direction.UP_RIGHT)) return true
        }
        for (x in boardPointRange) {
            if (this.isCompletedOmokOnThisLine(Point(x, 1), Direction.UP_RIGHT)) return true
        }
        return false
    }

    private fun Set<Stone>.isCompletedOmokOnThisLine(initPoint: Point, direction: Direction): Boolean {
        var point = initPoint
        var linkedCount = 0
        while (point in Board) {
            val stone = Stone(point)
            if (stone in this) linkedCount++ else linkedCount = 0
            if (linkedCount >= 5) return true
            point = point goTo direction
        }
        return false
    }

    companion object {
        private const val RUNNING_COMPLETE_ERROR = "게임이 진행되는 상태에서는 오목이 완성될 수 없습니다."
        private const val BLACK_WHITE_INTERSECT_ERROR = "흑돌과 백돌이 겹칠 수 없습니다."
        private const val ALREADY_PLACED_ERROR = "이미 놓여진 곳에 둘 수 없습니다."
    }
}
