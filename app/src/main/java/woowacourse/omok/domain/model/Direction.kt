package woowacourse.omok.domain.model

enum class Direction(
    val direction: List<Pair<Int, Int>>,
) {
    HORIZONTAL(listOf(Pair(-1, 0), Pair(1, 0))),
    VERTICAL(listOf(Pair(0, 1), Pair(0, -1))),
    DIAGONAL_UP(listOf(Pair(-1, -1), Pair(1, 1))),
    DIAGONAL_DOWN(listOf(Pair(-1, 1), Pair(1, -1))),
}
