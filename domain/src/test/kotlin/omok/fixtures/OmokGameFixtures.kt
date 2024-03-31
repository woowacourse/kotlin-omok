package omok.fixtures

import omock.model.board.Block
import omock.model.game.OmokGame

fun createOmokGame(vararg blocks: Block): OmokGame {
    return OmokGame(createBoard(blocks.toList()))
}
