package domain.turn

import domain.board.PlacedBoard
import domain.stone.Color

class BlackWin(
    board: PlacedBoard
) : FinishedTurn(board) {
    override val curColor: Color = Color.BLACK
}
