enum class Direction(
    val rowStep: Int,
    val colStep: Int,
) {
    UP(1, 0),
    DOWN(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(-1, 1),
    UP_LEFT(1, -1),
    ;

    fun nextPosition(position: Position): Position = Position(position.row + rowStep, position.col + colStep)

    fun isGoLeft(): Boolean = colStep == -1

    fun isGoRight(): Boolean = colStep == 1

    fun isGoUp(): Boolean = rowStep == 1

    fun isGoDown(): Boolean = rowStep == -1
}
