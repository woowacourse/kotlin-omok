package omok.domain.omokRule

object WhiteWinRule : OmokRule(WHITE_STONE, BLACK_STONE) {
    override fun validate(board: List<List<Int>>, position: RulePosition): Boolean =
        RuleDirection.DIRECTIONS.map { direction -> checkBlackWin(board, position, direction) }.contains(true)

    private fun checkBlackWin(board: List<List<Int>>, position: RulePosition, direction: RuleDirection): Boolean {
        return when {
            countLine(board, position, direction.opposite()) + countLine(board, position, direction) == 4 -> true
            else -> false
        }
    }
}
