class OmokGame(
    val getPoint: (Stone?) -> Point,
    val checkBoardState: (Board) -> Unit,
) {
    fun runGame(): Color {
        var board: Board = PlayingBoard(listOf())
        var color = Color.BLACK
        while (board.isFinished.not()) {
            checkBoardState(board)
            board = turnGame(board, color)
            color = !color
        }
        return board.winningColor
    }

    fun turnGame(board: Board, color: Color): Board {
        val point = getPoint(board.getLatestStone())
        return if (board.isPossiblePut(point)) {
            val nextBoard = board.putStone(Stone(point, color))
            checkBoardState(nextBoard)
            nextBoard
        } else {
            turnGame(board, color)
        }
    }
}
