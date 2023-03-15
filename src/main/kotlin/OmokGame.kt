class OmokGame(
    val getPoint: (Color, Point?) -> Point,
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
        return board.isWin
    }

    fun turnGame(board: Board, color: Color): Board {
        val point = getPoint(color, board.getLatestPoint(color))
        return if (board.isPossiblePut(point)) {
            val nextBoard = board.putStone(Stone(point, color))
            checkBoardState(nextBoard)
            nextBoard
        } else {
            turnGame(board, color)
        }
    }
}
