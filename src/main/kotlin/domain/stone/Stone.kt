package domain.stone

import domain.position.Position
import domain.position.Position.Companion.POSITION_RANGE

data class Stone private constructor(val position: Position) {

    fun countSameStones(
        stones: Stones,
        direction: Pair<Int, Int>,
        weight: Int,
    ): Int = position.countStraight(stones, direction, weight)

    companion object {
        private val STONES = POSITION_RANGE.map { row -> POSITION_RANGE.map { col -> Stone(Position(row, col)) } }

        fun of(row: Int, col: Int): Stone = STONES[row - 1][col - 1]
        fun of(position: Position): Stone = STONES[position.row - 1][position.col - 1]
    }
}
