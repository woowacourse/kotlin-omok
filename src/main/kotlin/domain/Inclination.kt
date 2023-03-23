package domain

enum class Inclination(val dx: Int, val dy: Int, val directions: List<Direction>) {
    HORIZONTAL(1, 0, listOf(Direction.LEFT, Direction.RIGHT)),
    VERTICAL(0, 1, listOf(Direction.UP, Direction.DOWN)),
    UPPER_RIGHT_DIAGONAL(1, 1, listOf(Direction.UP_RIGHT, Direction.DOWN_LEFT)),
    UPPER_LEFT_DIAGONAL(-1, 1, listOf(Direction.UP_LEFT, Direction.DOWN_RIGHT)),
}
