package domain.player

import domain.board.Board
import domain.stone.Color
import domain.stone.Point

class WhiteStonePlayer : Player() {

    override val color: Color = Color.White

    override fun isPossibleToPlace(board: Board, point: Point): Boolean {
        return !board.isPlaced(point)
    }
}
