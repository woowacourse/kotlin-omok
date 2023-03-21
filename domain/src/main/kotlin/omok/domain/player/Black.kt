package omok.domain.player

import omok.domain.board.Position
import omok.domain.judgment.PlacementReferee
import omok.model.toPresentation

object Black : Stone {
    override fun canPlace(referee: PlacementReferee, board: Map<Position, Stone?>, position: Position): Boolean {
        require(!referee.isForbiddenPlacement(board, position)) {
            "[ERROR] ${position.column.toPresentation()}${position.row.toPresentation()}은/는 금수입니다."
        }
        return true
    }
}
