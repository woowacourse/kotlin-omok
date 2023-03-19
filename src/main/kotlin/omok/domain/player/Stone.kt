package omok.domain.player

import omok.domain.board.Position
import omok.domain.judgment.PlacementReferee

sealed class Stone {
    abstract val name: String
    abstract fun canPlace(referee: PlacementReferee, storedStones: Map<Position, Stone?>, position: Position): Boolean
}
