package domain.player

import domain.board.Board
import domain.stone.Color
import domain.stone.Point

class WhiteStonePlayer : Player() {

    override val color: Color = Color.White

    override fun toNextPlayer(): Player {
        if(state == PlayerState.Placing){
            return this
        }
        return BlackStonePlayer()
    }

    override fun isPossibleToPlace(board: Board, placingPoint: Point): Boolean {
        return !board.isPlaced(placingPoint)
    }
}
