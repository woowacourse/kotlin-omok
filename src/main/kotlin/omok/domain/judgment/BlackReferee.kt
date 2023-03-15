package omok.domain.judgment

import omok.domain.board.Position
import omok.domain.player.Black
import omok.domain.player.Stone

class BlackReferee : PlacementReferee() {
    override fun isForbiddenPlacement(board: Map<Position, Stone?>, position: Position): Boolean {
        val virtualBoard = board.toMutableMap()
        virtualBoard[position] = Black
        if (countEveryContinuity(virtualBoard, position, 0).any { it == 5 }) return false
        return listOf(
            ThreeThreeReferee().isForbiddenPlacement(virtualBoard, position),
            FourFourReferee().isForbiddenPlacement(virtualBoard, position),
            OverFiveReferee().isForbiddenPlacement(virtualBoard, position)
        ).any { it }
    }
}
