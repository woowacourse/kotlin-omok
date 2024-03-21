package omok.model

class DoubleOpenThreeForbiddenPlace : ForbiddenPlace {
    override fun availablePosition(board: Board, position: Position): Boolean {
        return true
    }
}
