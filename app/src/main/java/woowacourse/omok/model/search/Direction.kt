package woowacourse.omok.model.search

enum class Direction(
    val x: Int,
    val y: Int,
) {
    TOP(1, 0),
    TOP_RIGHT(1, 1),
    RIGHT(0, 1),
    RIGHT_BOTTOM(-1, 1),
    BOTTOM(-1, 0),
    BOTTOM_LEFT(-1, -1),
    LEFT(0, -1),
    LEFT_TOP(1, -1), ;

    companion object {
        fun reverse(direction: Direction): Direction {
            return when (direction) {
                TOP -> BOTTOM
                TOP_RIGHT -> BOTTOM_LEFT
                RIGHT -> LEFT
                RIGHT_BOTTOM -> LEFT_TOP
                BOTTOM -> TOP
                BOTTOM_LEFT -> TOP_RIGHT
                LEFT -> RIGHT
                LEFT_TOP -> RIGHT_BOTTOM
            }
        }
    }
}
