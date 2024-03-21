package omok.model

class ThreeThreeForbiddenPlace : ForbiddenPlace {
    override fun availablePosition(board: Board, position: Position): Boolean {
        return true
    }
}
