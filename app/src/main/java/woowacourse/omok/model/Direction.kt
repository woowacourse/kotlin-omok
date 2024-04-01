package woowacourse.omok.model

enum class Direction(val dx: Int, val dy: Int) {
    TOP(1, 0),
    TOP_RIGHT(1, 1),
    RIGHT(0, 1),
    RIGHT_BOTTOM(-1, 1),
    BOTTOM(-1, 0),
    BOTTOM_LEFT(-1, -1),
    LEFT(0, -1),
    LEFT_TOP(1, -1),
    ;

    val opposite: Direction
        get() = when (this) {
            TOP -> BOTTOM
            RIGHT -> LEFT
            BOTTOM -> TOP
            LEFT -> RIGHT
            TOP_RIGHT -> BOTTOM_LEFT
            RIGHT_BOTTOM -> LEFT_TOP
            BOTTOM_LEFT -> TOP_RIGHT
            LEFT_TOP -> RIGHT_BOTTOM
        }
}
