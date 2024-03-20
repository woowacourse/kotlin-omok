package omok.model

enum class Direction(val row: Int, val col: Int) {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    LEFT_UP(-1, -1),
    RIGHT_UP(-1, 1),
    LEFT_DOWN(1, -1),
    RIGHT_DOWN(1, 1),
    ;

    fun reverse(): Direction {
        return when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
            LEFT_UP -> RIGHT_DOWN
            RIGHT_UP -> LEFT_DOWN
            LEFT_DOWN -> RIGHT_UP
            RIGHT_DOWN -> LEFT_UP
        }
    }

    companion object {
        fun biDirections(): List<BiDirection> {
            return listOf(
                BiDirection(UP, DOWN),
                BiDirection(LEFT, RIGHT),
                BiDirection(LEFT_UP, RIGHT_DOWN),
                BiDirection(RIGHT_UP, LEFT_DOWN),
            )
        }
    }
}

data class BiDirection(val direction1: Direction, val direction2: Direction)
