package domain.player

import domain.board.Board
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

class BlackStonePlayer(
    decidePlacingPoint: (latestStone: Stone?) -> Point,
    checkBoard: (currentBoard: Board) -> Unit,
) : Player(decidePlacingPoint, checkBoard) {

    override val color: Color = Color.BLACK

    override fun isPossibleToPlace(board: Board, placingPosition: Point): Boolean {
        if (board.isPlaced(placingPosition) &&
            BlackStonePlacingPoint.valueOf(board, placingPosition) == BlackStonePlacingPoint.ALLOWED
        ) {
            return true
        }
        return false
    }
}
