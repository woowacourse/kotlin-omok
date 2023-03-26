package omok.domain.omokRule

object WhiteWinRule : OmokRule(WHITE_STONE, BLACK_STONE) {
    override fun validate(board: List<List<Int>>, position: RulePosition): Boolean =
        RuleDirection.DIRECTIONS.map { direction -> checkBlackWin(board, position, direction) }.contains(true)

    private fun checkBlackWin(board: List<List<Int>>, position: RulePosition, direction: RuleDirection): Boolean {
        val (stone1, blink1) = search(board, position, direction.opposite())
        val (stone2, blink2) = search(board, position, direction)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 >= 4 -> true
            else -> false
        }
    }
}
