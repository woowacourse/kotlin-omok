package omok.model.stone

import omok.model.game.Board

data class Coordinate(val x: Int, val y: Int) {
    constructor(index: Pair<Int, Int>): this(index.first, index.second)

    companion object {
        private val BOARD_SIZE_RANGE: IntRange = 1..Board.BOARD_LENGTH

        fun of(mark: String): Coordinate {
            val first = mark.first().uppercaseChar() - 'A' + 1
            val second = mark.substring(1)
            require(first.isWithinRange() && second.isWithinRange()) { "범위 내의 위치를 입력해주세요. (잘못된 값: $mark)" }

            return Coordinate(first - 1, second.toInt() - 1)
        }

        private fun Int.isWithinRange(): Boolean = this in BOARD_SIZE_RANGE

        private fun String.isWithinRange(): Boolean {
            val number = this.toIntOrNull()
            return number?.isWithinRange() ?: false
        }
    }
}
