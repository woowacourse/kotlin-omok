package domain.player

import domain.board.Board
import domain.stone.Color
import domain.stone.Point

class BlackStonePlayer : Player() {

    override val color: Color = Color.BLACK

    override fun isPossibleToPlace(board: Board, placingPoint: Point): Boolean {
        if (!board.isPlaced(placingPoint) &&
            PlacingPoint.valueOf(board, placingPoint) == PlacingPoint.ALLOWED
        ) {
            return true
        }
        return false
    }
}
