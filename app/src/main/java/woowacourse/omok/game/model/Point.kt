package woowacourse.omok.game.model

data class Point(val row: Int, val col: Int) {
    fun index(): Int = row * 15 + col

    companion object {
        fun from(index: Int): Point {
            return Point(row = index / 15, col = index % 15)
        }
    }
}
