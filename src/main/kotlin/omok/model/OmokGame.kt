package omok.model

class OmokGame(
    private var turn: Turn = BlackTurn(),
    private val board: Board = Board(),
) {
    fun play(
        updateBoard: (Board) -> Unit,
        updateTurn: (Turn) -> Unit,
        getPoint: () -> Point,
    ) {
        while (turn !is FinishedTurn) {
            updateBoard(board)
            updateTurn(turn)
            val point = getPoint()
            turn = turn.putStone(point, board)
        }
        updateTurn(turn)
    }
}
