package domain.state

import domain.Board
import domain.Stone

abstract class Running(blackStones: Set<Stone>, whiteStones: Set<Stone>) : State {

    init {
        require(blackStones.completeOmok().not() && whiteStones.completeOmok().not()) { RUNNING_COMPLETE_ERROR }
        require(blackStones.intersect(whiteStones).isEmpty()) { BLACK_WHITE_INTERSECT_ERROR }
    }

    fun checkAlreadyPlaced(stone: Stone) {
        require(stone !in blackStones && stone !in whiteStones) { ALREADY_PLACED_ERROR }
    }

    protected fun Set<Stone>.completeOmok(): Boolean {
        val boardPointRange = 1..Board.SIZE
        for (x in boardPointRange) {
            if (fun2(this, x)) return true
        }
        for (y in boardPointRange) {
            if (fun1(this, y)) return true
        }
        // 왼쪽 위에서 시작하는 대각선
        for ((maxX, maxY) in boardPointRange.zip(boardPointRange)) {
            var linkedCount = 0
            for ((x, y) in (1..maxX).zip(maxY downTo 1)) {
                val stone = Stone(x, y)
                if (stone in this) linkedCount++ else linkedCount = 0
                if (linkedCount >= 5) return true
            }
        }
        for ((minX, minY) in boardPointRange.zip(boardPointRange)) {
            var linkedCount = 0
            for ((x, y) in (minX..Board.SIZE).zip(Board.SIZE downTo minY).toList()) {
                val stone = Stone(x, y)
                if (stone in this) linkedCount++ else linkedCount = 0
                if (linkedCount >= 5) return true
            }
        }

        // 왼쪽 아래에서 시작하는 대각선
        for ((maxX, minY) in boardPointRange.zip(boardPointRange.reversed())) {
            var linkedCount = 0
            for ((x, y) in (1..maxX).zip(minY..Board.SIZE)) {
                val stone = Stone(x, y)
                if (stone in this) linkedCount++ else linkedCount = 0
                if (linkedCount >= 5) return true
            }
        }
        for ((minX, maxY) in boardPointRange.zip(boardPointRange.reversed())) {
            var linkedCount = 0
            for ((x, y) in (minX..Board.SIZE).zip(1..maxY)) {
                val stone = Stone(x, y)
                if (stone in this) linkedCount++ else linkedCount = 0
                if (linkedCount >= 5) return true
            }
        }
        return false
    }

    private fun fun1(placedStones: Set<Stone>, y: Int): Boolean {
        var linkedCount = 0
        for (x in 1..Board.SIZE) {
            val stone = Stone(x, y)
            if (stone in placedStones) linkedCount++ else linkedCount = 0
            if (linkedCount >= 5) return true
        }
        return false
    }

    private fun fun2(placedStones: Set<Stone>, x: Int): Boolean {
        var linkedCount = 0
        for (y in 1..Board.SIZE) {
            val stone = Stone(x, y)
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
