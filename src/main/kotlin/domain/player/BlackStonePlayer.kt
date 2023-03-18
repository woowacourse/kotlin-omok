package domain.player

import domain.board.Board
import domain.stone.Color
import domain.stone.Point

class BlackStonePlayer() : Player() {

    override val color: Color = Color.BLACK

    override fun isPossibleToPlace(board: Board, placingPosition: Point): Boolean {
        if (super.isPossibleToPlace(board, placingPosition) &&
            PlacingPoint.valueOf(board, placingPosition) == PlacingPoint.ALLOWED
        ) {
            return true
        }
        return false
    }
}
