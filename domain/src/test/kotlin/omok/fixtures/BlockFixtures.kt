package omok.fixtures

import omock.model.Position
import omock.model.board.Block
import omock.model.board.BlockState

fun createBlock(
    x: Int,
    y: Int,
    state: BlockState,
): Block = Block(Position(x, y), state)

fun createBlackBlock(
    x: Int,
    y: Int,
): Block = Block(Position(x, y), BlockState.BLACK_STONE)

fun createBlackBlock(position: Position): Block = Block(position, BlockState.BLACK_STONE)

fun createWhiteBlock(
    x: Int,
    y: Int,
): Block = Block(Position(x, y), BlockState.WHITE_STONE)

fun createWhiteBlock(position: Position): Block = Block(position, BlockState.WHITE_STONE)
