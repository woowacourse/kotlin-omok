package omok.model

interface WinningCondition {
    fun isWin(board: Board, position: Position): Boolean
}
