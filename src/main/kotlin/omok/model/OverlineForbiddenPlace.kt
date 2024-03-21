package omok.model

class OverlineForbiddenPlace : ForbiddenPlace {
    override fun availablePosition(board: Board, position: Position): Boolean {
        return RuleAdapter.abideOverLineRule(board, position)
    }
}
