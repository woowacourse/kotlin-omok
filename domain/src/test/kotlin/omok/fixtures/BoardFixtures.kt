package omok.fixtures

import omock.model.board.Block
import omock.model.board.OmokBoard
import omock.model.board.buildOmokBoard

private const val DEFAULT_BOARD_SIZE = 15

fun createBoard(blocks: List<Block>): OmokBoard =
    buildOmokBoard {
        size(DEFAULT_BOARD_SIZE)
        blocks(blocks)
    }

private fun createEmptyBoard() = buildOmokBoard { }

fun createBoard(vararg blocks: Block): OmokBoard {
    if (blocks.isEmpty()) return createEmptyBoard()
    return createBoard(blocks.toList())
}

fun createBoard(
    size: Int,
    vararg blocks: Block,
): OmokBoard {
    if (blocks.isEmpty()) return createEmptyBoard()
    val map = blocks.toList()
    return buildOmokBoard {
        size(size)
        blocks(map)
    }
}
