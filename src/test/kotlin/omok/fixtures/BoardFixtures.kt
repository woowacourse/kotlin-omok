package omok.fixtures

import omok.model.Board
import omok.model.OmokStone

private const val DEFAULT_BOARD_SIZE = 15

private fun createBoard(omokStones: List<OmokStone>): Board = createBoard(*omokStones.toTypedArray())

private fun createEmptyBoard() = Board(DEFAULT_BOARD_SIZE, stones = emptyMap())

fun createBoard(vararg omokStones: OmokStone): Board {
    if (omokStones.isEmpty()) return createEmptyBoard()
    val map = omokStones.associateBy { it.position }
    return Board(DEFAULT_BOARD_SIZE, map)
}

fun createBoard(
    size: Int,
    vararg omokStones: OmokStone,
): Board {
    if (omokStones.isEmpty()) return createEmptyBoard()
    val map = omokStones.associateBy { it.position }
    return Board(size, map)
}
