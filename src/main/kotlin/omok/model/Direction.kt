package omok.model

enum class Direction(val x: Int, val y: Int) {
    TOP(1, 0),
    TOP_RIGHT(1, 1),
    RIGHT(0, 1),
    RIGHT_BOTTOM(-1, 1),
    BOTTOM(-1, 0),
    BOTTOM_LEFT(-1, -1),
    LEFT(0, -1),
    LEFT_TOP(1, -1),
    ;

    companion object {
        fun biDirection(): List<BiDirection> {
            return listOf(
                BiDirection(TOP, BOTTOM),
                BiDirection(TOP_RIGHT, BOTTOM_LEFT),
                BiDirection(RIGHT, LEFT),
                BiDirection(RIGHT_BOTTOM, LEFT_TOP),
            )
        }
    }
}

data class BiDirection(val direction1: Direction, val direction2: Direction)
