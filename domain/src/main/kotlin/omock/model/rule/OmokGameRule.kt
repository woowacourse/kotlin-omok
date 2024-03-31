package omock.model.rule

import omock.model.PlaceResult
import omock.model.Position
import omock.model.Stone
import omock.model.board.OmokBoard

sealed interface OmokGameRule {
    fun placeStone(
        position: Position,
        stone: Stone,
        board: OmokBoard,
    ): PlaceResult
}
