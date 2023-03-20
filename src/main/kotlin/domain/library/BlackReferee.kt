package domain.library

import domain.stone.Color
import domain.stone.Position

class BlackReferee : PlacementReferee() {
    override fun isForbiddenPlacement(board: Map<Position, Color?>, position: Position): Boolean {
        val virtualBoard = board.toMutableMap()
        virtualBoard[position] = Color.BLACK
        if (countEveryContinuity(virtualBoard, position, 0).any { it == 5 }) return false
        return listOf(
            ThreeThreeReferee().isForbiddenPlacement(virtualBoard, position),
            FourFourReferee().isForbiddenPlacement(virtualBoard, position),
            OverFiveReferee().isForbiddenPlacement(virtualBoard, position)
        ).any { it }
    }
}
