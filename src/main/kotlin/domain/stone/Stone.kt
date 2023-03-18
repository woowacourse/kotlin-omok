package domain.stone

import domain.position.Position
import domain.position.Position.Companion.POSITION_RANGE

data class Stone private constructor(val position: Position) {

    companion object {
        private val STONES = POSITION_RANGE.map { row -> POSITION_RANGE.map { col -> Stone(Position(row, col)) } }

        fun of(row: Int, col: Int): Stone = STONES[row - 1][col - 1]
        fun of(position: Position): Stone = STONES[position.row - 1][position.col - 1]
    }
}
