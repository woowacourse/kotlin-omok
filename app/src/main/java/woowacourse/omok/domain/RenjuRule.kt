package omok.domain

interface RenjuRule {
    fun checkWin(
        board: Board,
        position: Position,
    ): Boolean

    fun checkDoubleFourFoul(
        board: Board,
        position: Position,
    ): Boolean

    fun checkDoubleThreeFoul(
        board: Board,
        position: Position,
    ): Boolean

    fun checkOverline(
        board: Board,
        position: Position,
    ): Boolean
}
