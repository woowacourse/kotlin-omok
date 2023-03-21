package omok.domain.player

import omok.domain.board.Position
import omok.domain.judgment.PlacementReferee

object White : Stone {
    override fun canPlace(referee: PlacementReferee, board: Map<Position, Stone?>, position: Position): Boolean = true
}
