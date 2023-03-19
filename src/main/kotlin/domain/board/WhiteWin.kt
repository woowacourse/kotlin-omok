package domain.board

import domain.stone.Color

class WhiteWin(
    board: PlacedBoard
) : FinishedTurn(board) {
    override val curColor: Color = Color.WHITE
}
