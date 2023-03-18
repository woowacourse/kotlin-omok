package domain.player

import domain.board.Board
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

class WhiteStonePlayer(
    decidePosition: (latestStone: Stone?) -> Point,
    checkBoard: (currentBoard: Board) -> Unit,
) : Player(decidePosition, checkBoard) {

    override val color: Color = Color.WHITE

    override fun isPossibleToPlace(board: Board, placingPosition: Point): Boolean {
        if (!board.isPlaced(placingPosition)) {
            return true
        }
        return false
    }
}
