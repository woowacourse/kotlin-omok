package omok.domain.judgment

import omok.domain.board.Position
import omok.domain.player.Black
import omok.domain.player.Stone

class BlackReferee : PlacementReferee(Black) {
    override fun isForbiddenPlacement(board: Map<Position, Stone?>, position: Position): Boolean {
        val virtualBoard = board.toMutableMap()
        virtualBoard[position] = target
        if (countEveryContinuity(virtualBoard, position, 0).any { it == 5 }) return false
        return listOf(
            ThreeThreeReferee(target).isForbiddenPlacement(virtualBoard, position),
            FourFourReferee(target).isForbiddenPlacement(virtualBoard, position),
            OverFiveReferee(target).isForbiddenPlacement(virtualBoard, position)
        ).any { it }
    }
}
