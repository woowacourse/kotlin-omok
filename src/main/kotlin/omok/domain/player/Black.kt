package omok.domain.player

import omok.domain.board.Position
import omok.domain.judgment.PlacementReferee

object Black : Stone {
    override val name = "흑"
    override fun canPlace(referee: PlacementReferee, storedStones: Map<Position, Stone?>, position: Position): Boolean {
        require(!referee.isForbiddenPlacement(storedStones, position)) {
            "[ERROR] ${position.column.name}${position.row.axis + 1}은/는 금수입니다."
        }
        return true
    }
}
