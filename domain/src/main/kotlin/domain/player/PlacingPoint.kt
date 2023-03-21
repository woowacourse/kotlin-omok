package domain.player

import domain.board.Board
import domain.library.adapter.BlackRefreeAdapter
import domain.stone.Point

enum class PlacingPoint {
    FORBIDDEN,
    ALLOWED;

    companion object {
        private val BlackRefreeAdapter: BlackRefreeAdapter = BlackRefreeAdapter()

        fun valueOf(currentBoard: Board, placingPoint: Point): PlacingPoint {
            if (BlackRefreeAdapter.isForbiddenPlacement(currentBoard, placingPoint)) {
                return FORBIDDEN
            }
            return ALLOWED
        }
    }
}
