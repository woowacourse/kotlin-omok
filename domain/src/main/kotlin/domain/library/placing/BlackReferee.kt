package domain.library.placing

import domain.library.position.Position
import domain.stone.Color

class BlackReferee : PlacementReferee() {
    override fun isForbiddenPlacement(board: Map<Position, Color?>, position: Position): Boolean {
        val virtualBoard = board.toMutableMap()
        virtualBoard[position] = Color.Black
        if (countEveryContinuity(virtualBoard, position, 0).any { it == 5 }) return false
        return listOf(
            ThreeThreeReferee().isForbiddenPlacement(virtualBoard, position),
            FourFourReferee().isForbiddenPlacement(virtualBoard, position),
            OverFiveReferee().isForbiddenPlacement(virtualBoard, position)
        ).any { it }
    }
}
