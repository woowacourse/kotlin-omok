package omock.model.rule

import omock.model.PlaceResult
import omock.model.Position
import omock.model.Stone
import omock.model.board.OmokBoard

data object RenjuRule : OmokGameRule {
    override fun placeStone(
        position: Position,
        stone: Stone,
        board: OmokBoard,
    ): PlaceResult {
        if (stone == Stone.BLACK) return BlackRenjuRule.placeStone(position, stone, board)
        return WhiteRenjuRule.placeStone(position, stone, board)
    }
}
