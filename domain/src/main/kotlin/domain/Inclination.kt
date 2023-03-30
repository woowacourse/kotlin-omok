package domain

enum class Inclination(val oneDirection: Direction, val otherDirection: Direction) {
    HORIZONTAL(Direction.LEFT, Direction.RIGHT),
    VERTICAL(Direction.UP, Direction.DOWN),
    UPPER_RIGHT_DIAGONAL(Direction.UP_RIGHT, Direction.DOWN_LEFT),
    UPPER_LEFT_DIAGONAL(Direction.UP_LEFT, Direction.DOWN_RIGHT),
}
