package woowacourse.omok.domain.model

class OmokGame(
    private var turn: Turn = BlackTurn(),
    private val board: Board = Board(),
) {
    fun tryPlayTurn(
        updateBoard: (Board) -> Unit,
        updateTurn: (Turn, Stone?) -> Unit,
        getPoint: () -> Point,
        displayMessage: (String) -> Unit,
    ) {
        val point = getPoint()
        turn = turn.putStone(point, board)
        when (val gameResult = turn.forbiddenResult) {
            Success -> {
                updateBoard(board)
                updateTurn(turn, board.latestStone)
            }
            is Failure -> displayMessage(gameResult.message)
        }
    }

    fun isGameFinished(): Boolean = turn is FinishedTurn
}
