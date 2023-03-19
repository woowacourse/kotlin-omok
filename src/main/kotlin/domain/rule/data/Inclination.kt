package domain.rule.data


enum class Inclination(val directions: List<Direction>) {
    HORIZONTAL(listOf(Direction.LEFT, Direction.RIGHT)),
    VERTICAL(listOf(Direction.UP, Direction.DOWN)),
    UPPER_RIGHT_DIAGONAL(listOf(Direction.UP_RIGHT, Direction.DOWN_LEFT)),
    UPPER_LEFT_DIAGONAL(listOf(Direction.UP_LEFT, Direction.DOWN_RIGHT)),
}
