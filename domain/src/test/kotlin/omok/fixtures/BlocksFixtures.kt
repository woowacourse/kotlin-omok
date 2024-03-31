package omok.fixtures

import omock.model.board.Block
import omock.model.board.Blocks

fun createBlocks(vararg blocks: Block): Blocks = Blocks(blocks.toList())
