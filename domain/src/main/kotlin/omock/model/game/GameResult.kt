package omock.model.game

import omock.model.board.Block
import omock.model.board.OmokBoard

data class GameResult(
    val board: OmokBoard,
    val lastBlock: Block,
)
