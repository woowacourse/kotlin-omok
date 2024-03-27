package omok.fixtures

import omok.model.OmokStone
import omok.model.board.Board
import omok.model.board.BoardSize
import omok.model.board.Boxes

private val DEFAULT_BOARD_SIZE = BoardSize(15)

private fun createBoard(omokStones: List<OmokStone>): Board = createBoard(*omokStones.toTypedArray())

private fun createEmptyBoard() = Board(DEFAULT_BOARD_SIZE, boxes = Boxes())

fun createBoard(vararg omokStones: OmokStone): Board {
    if (omokStones.isEmpty()) return createEmptyBoard()
    val map = omokStones.associateBy { it.position }.let(::Boxes)
    return Board(DEFAULT_BOARD_SIZE, map)
}

fun createBoard(
    size: Int,
    vararg omokStones: OmokStone,
): Board {
    if (omokStones.isEmpty()) return createEmptyBoard()
    val map = omokStones.associateBy { it.position }.let(::Boxes)
    return Board(BoardSize(size), map)
}
