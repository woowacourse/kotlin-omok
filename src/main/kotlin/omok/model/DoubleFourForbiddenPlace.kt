package omok.model

class DoubleFourForbiddenPlace : ForbiddenPlace {
    override fun availablePosition(board: Board, position: Position): Boolean {
        return true
    }
}
