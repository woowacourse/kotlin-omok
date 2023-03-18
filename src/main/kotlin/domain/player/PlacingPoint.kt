package domain.player

import domain.board.Board
import domain.library.adapter.BlackRefreeAdapter
import domain.stone.Point

enum class PlacingPoint {
    FORBIDDEN,
    ALLOWED;

    companion object {
        private val blackRefreeAdapter: BlackRefreeAdapter = BlackRefreeAdapter()

        fun valueOf(currentBoard: Board, placingPoint: Point): PlacingPoint {
            if (blackRefreeAdapter.isForbiddenPlacement(currentBoard, placingPoint)) {
                return FORBIDDEN
            }
            return ALLOWED
        }
    }
}
