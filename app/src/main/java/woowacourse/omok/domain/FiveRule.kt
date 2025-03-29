package woowacourse.omok.domain

import woowacourse.omok.domain.Board.Companion.BOARD_SIZE

class FiveRule {
    fun isOmok(
        stone: Stone,
        stones: Stones,
    ): Boolean {
        directions.forEach { direction ->
            var count = 1

            count += stonesCount(stone, stones, direction)
            count += stonesCount(stone, stones, listOf(-direction[0], -direction[1]))

            if (count >= OMOK_WINNING_CONDITION) return true
        }
        return false
    }

    private fun stonesCount(
        stone: Stone,
        stones: Stones,
        direction: List<Int>,
    ): Int {
        var currentPosition = Pair(stone.position.row + direction[0], stone.position.column + direction[1])
        var count = 0

        while (currentPosition.first in 0 until BOARD_SIZE && currentPosition.second in 0 until BOARD_SIZE &&
            stones.stones.any { it == Stone(Position(currentPosition.first, currentPosition.second), stone.color) }
        ) {
            count++
            currentPosition = Pair(currentPosition.first + direction[0], currentPosition.second + direction[1])
        }
        return count
    }

    companion object {
        private val directions = listOf(listOf(1, 0), listOf(1, 1), listOf(0, 1), listOf(1, -1))
        private const val OMOK_WINNING_CONDITION = 5
    }
}
