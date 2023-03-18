package domain.library.judgment

import domain.library.position.Position
import domain.stone.Color

class OverFiveReferee : PlacementReferee() {
    override fun isForbiddenPlacement(board: Map<Position, Color?>, position: Position): Boolean {
        return countEveryContinuity(board, position, 0).any { it > 5 }
    }
}
