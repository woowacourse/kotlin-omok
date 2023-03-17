package domain.library

import domain.stone.Color
import domain.stone.Position

class OverFiveReferee : PlacementReferee() {
    override fun isForbiddenPlacement(board: Map<Position, Color?>, position: Position): Boolean {
        return countEveryContinuity(board, position, 0).any { it > 5 }
    }
}
