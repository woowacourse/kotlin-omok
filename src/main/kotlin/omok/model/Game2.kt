package omok.model

class Game2(val board: Board) {
    val blackPlayer = Player2(Color.BLACK)
    val whitePlayer = Player2(Color.WHITE)

    fun processTurn(position: Position) {
        when (board.lastStone?.color) {
            null -> blackPlayer.makeMove(board, position)
            Color.BLACK -> whitePlayer.makeMove(board, position)
            Color.WHITE -> blackPlayer.makeMove(board, position)
        }
    }
}
