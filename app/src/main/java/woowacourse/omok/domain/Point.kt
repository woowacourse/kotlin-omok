package woowacourse.omok.domain

data class Point(
    val row: Int,
    val col: Int,
) {
    fun isInBounds(size: Int): Boolean = row in 0 until size && col in 0 until size
}
