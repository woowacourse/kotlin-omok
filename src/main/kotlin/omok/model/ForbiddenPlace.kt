package omok.model

interface ForbiddenPlace {
    fun availablePosition(board: Board, position: Position): Boolean
}