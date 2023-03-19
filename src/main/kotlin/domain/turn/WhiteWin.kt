package domain.turn

import domain.board.PlacedBoard
import domain.stone.Color

class WhiteWin(
    board: PlacedBoard
) : FinishedTurn(board) {
    override val curColor: Color = Color.WHITE
}
