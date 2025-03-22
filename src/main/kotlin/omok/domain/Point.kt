package omok.domain

data class Point(
    val row: Int,
    val col: Int,
) {
    companion object {
        fun Point.toPair(): Pair<Int, Int> = row to col
    }
}
