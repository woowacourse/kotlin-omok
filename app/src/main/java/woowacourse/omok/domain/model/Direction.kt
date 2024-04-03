package woowacourse.omok.domain.model

enum class Direction(val dx: Int, val dy: Int) {
    HORIZONTAL(1, 0),
    VERTICAL(0, 1),
    DIAGONAL_UP(1, 1),
    DIAGONAL_DOWN(1, -1),
}
