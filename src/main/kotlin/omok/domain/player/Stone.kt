package omok.domain.player

import omok.domain.board.Position
import omok.domain.judgment.PlacementReferee

interface Stone {
    val name: String
    fun canPlace(referee: PlacementReferee, storedStones: Map<Position, Stone?>, position: Position): Boolean
}
