package woowacourse.omok.model

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

    fun nextPosition(position: Position): Position =
        Position(position.row + rowStep, position.col + colStep)

    fun isGoing(direction: Direction): Boolean =
        when {
            direction.colStep * this.colStep == 1 -> true
            direction.rowStep * this.rowStep == 1 -> true
            else -> false
        }

    fun opposite(): Direction =
        when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
            UP_LEFT -> DOWN_RIGHT
            DOWN_RIGHT -> UP_LEFT
            UP_RIGHT -> DOWN_LEFT
            DOWN_LEFT -> UP_RIGHT
        }
}
