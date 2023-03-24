package omok.domain.judgment

import omok.domain.board.Position
import omok.domain.player.Stone

class NormalReferee(target: Stone) : PlacementReferee(target) {
    override fun isForbiddenPlacement(board: Map<Position, Stone?>, position: Position): Boolean {
        return false
    }
}
