package omok.domain.omokRule

object BlackWinRule : OmokRule() {
    override fun validate(board: List<List<Int>>, position: RulePosition): Boolean =
        RuleDirection.DIRECTIONS.map { direction -> checkWhiteWin(board, position, direction) }.contains(true)

    private fun checkWhiteWin(board: List<List<Int>>, position: RulePosition, direction: RuleDirection): Boolean {
        return when {
            countLine(board, position, direction.opposite()) + countLine(board, position, direction) == 4 -> true
            else -> false
        }
    }
}
