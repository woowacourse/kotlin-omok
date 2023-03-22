package omok.domain.player

import omok.domain.board.Position
import omok.domain.judgment.PlacementReferee

object Black : Stone {
    override val id = 0

    override fun canPlace(referee: PlacementReferee, board: Map<Position, Stone?>, position: Position): Boolean {
        require(!referee.isForbiddenPlacement(board, position)) {
            "해당 위치는 금수 입니다."
        }
        return true
    }
}
