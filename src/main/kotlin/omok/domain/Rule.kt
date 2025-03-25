package omok.domain

interface Rule {
    fun isValidMove(
        board: Board,
        position: Position,
        color: StoneType,
    ): Boolean

    fun checkWin(
        board: Board,
        lastMove: Position,
        color: StoneType,
    ): Boolean
}
