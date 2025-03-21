package omok.model

class Player2(val color: Color) {
    fun makeMove(
        board: Board,
        position: Position,
    ) {
        board.add(Stone2(position, color))
    }
}
