package omok.model.rule

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.Stone

typealias Direction = Point

val directions =
    listOf(
        Direction(1, 0),
        Direction(0, 1),
        Direction(1, 1),
        Direction(-1, 1),
    )

fun isLineWithoutBlank(
    board: Board,
    direction: Direction,
    stone: Stone,
    length: Int,
): Boolean {
    val stoneCount = countStone(board, stone, direction, length)
    val blankCount = countBlank(board, stone.point, direction, length)
    return stoneCount == length && blankCount == 0
}

fun isLineWithBlank(
    board: Board,
    direction: Direction,
    stone: Stone,
    length: Int,
): Boolean {
    val stoneCount = countStone(board, stone, direction, length)
    val blankCount = countBlank(board, stone.point, direction, length)
    return stoneCount == length - 1 && blankCount == 1
}

fun isLineWithoutBlankWithPadding(
    board: Board,
    direction: Direction,
    stone: Stone,
    length: Int,
): Boolean {
    val endPoint = stone.point + direction * length
    val hasPadding = isEmptyLine(board, endPoint, direction, 2)
    return isLineWithoutBlank(board, direction, stone, length) && hasPadding
}

fun isLineWithBlankWithPadding(
    board: Board,
    direction: Direction,
    stone: Stone,
    length: Int,
): Boolean {
    val endPoint = stone.point + direction * length
    val hasPadding = isEmptyLine(board, endPoint, direction, 2)
    return isLineWithBlank(board, direction, stone, length) && hasPadding
}

fun countStone(
    board: Board,
    origin: Stone,
    direction: Direction,
    distance: Int,
): Int =
    (1..distance)
        .map {
            val point = origin.point + direction * it
            Stone(point, origin.stoneColor)
        }.count {
            board.contains(it)
        }

fun countBlank(
    board: Board,
    origin: Point,
    direction: Direction,
    distance: Int,
): Int =
    (1..distance)
        .map {
            origin + direction * it
        }.count {
            board.isPointInBoard(it) && board.isPointEmpty(it)
        }

fun isEmptyLine(
    board: Board,
    origin: Point,
    direction: Direction,
    distance: Int,
): Boolean =
    (1..distance).map {
        origin + direction * it
    }.all {
        board.isPointInBoard(it) && board.isPointEmpty(it)
    }
